package xyz.sorridi.stone.data.base;

import xyz.sorridi.stone.threading.Pipeline;

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

    public DataWorker(DataOrigin origin)
    {
        this(origin, 1, 1);
    }

    public DataWorker(DataOrigin origin, int writeThreads, int readThreads)
    {
        this.origin = origin;
        pipeline = new Pipeline("data_worker-" + id.getAndIncrement(), readThreads, writeThreads);
    }

    /**
     * Submits a data action to the pipeline.
     *
     * @param action The action to submit.
     * @param type   The pipeline type.
     * @param <T>    The type of the result.
     * @return The future result.
     */
    public <T> CompletableFuture<Optional<T>> submit(DataAction<T> action, Pipeline.Types type)
    {
        CompletableFuture<Optional<T>> future = new CompletableFuture<>();

        CompletableFuture
                .runAsync(() ->
                          {
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
     * Shuts down the pipeline.
     */
    public void shutdown()
    {
        pipeline.shutdown();
    }

}
