package xyz.sorridi.stone.common.data.base;

import xyz.sorridi.stone.common.data.base.op.DataResult;
import xyz.sorridi.stone.common.threading.Pipeline;
import xyz.sorridi.stone.common.data.base.op.DataAction;

import java.sql.Connection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A worker for data actions.
 *
 * @author Sorridi
 * @since 1.0
 */
public class DataWorker
{
    private static AtomicInteger id = new AtomicInteger(0);

    private final DataOrigin origin;
    private final Pipeline pipeline;

    private final boolean startupWorker;

    public DataWorker(DataOrigin origin)
    {
        this(origin, 1, 1, false);
    }

    public DataWorker(DataOrigin origin, boolean startupWorker)
    {
        this(origin, 1, 1, startupWorker);
    }

    public DataWorker(DataOrigin origin, int writeThreads, int readThreads)
    {
        this(origin, writeThreads, readThreads, false);
    }

    public DataWorker(DataOrigin origin, int writeThreads, int readThreads, boolean startupWorker)
    {
        this.origin = origin;
        this.startupWorker = startupWorker;
        pipeline = new Pipeline("data_worker-" + id.getAndIncrement(), readThreads, writeThreads);
    }

    /**
     * Submits a data action to the pipeline and returns a result.
     *
     * @param action The action to submit.
     * @param type   The pipeline type.
     * @param <T>    The type of the result.
     * @return The future result.
     */
    public <T> CompletableFuture<Optional<T>> submit(DataResult<T> action, Pipeline.Types type)
    {
        CompletableFuture<Optional<T>> future = new CompletableFuture<>();

        CompletableFuture
                .runAsync(() ->
                          {
                              waitIfNotReady();

                              try (Connection connection = origin.getConnection())
                              {
                                  T result = action.run(connection, origin);
                                  future.complete(Optional.ofNullable(result));
                              }
                              catch (Exception e)
                              {
                                  future.completeExceptionally(e);
                              }
                          }, pipeline.get(type));

        return future;
    }

    /**
     * Submits a data action to the pipeline with no type.
     *
     * @param action The action to submit.
     * @param type   The pipeline type.
     * @return The future result.
     */
    public CompletableFuture<Void> submit(DataAction action, Pipeline.Types type)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();

        future.thenRunAsync(() ->
                            {
                                waitIfNotReady();

                                try (Connection connection = origin.getConnection())
                                {
                                    action.run(connection, origin);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }, pipeline.get(type));

        return future;
    }

    /**
     * Shuts down the pipeline.
     */
    public void shutdown()
    {
        pipeline.shutdown();
    }

    /**
     * Waits until the origin is ready.
     */
    private void waitIfNotReady()
    {
        while (!startupWorker && !origin.isReady())
        {
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException ignored)
            {
            }
        }
    }

}
