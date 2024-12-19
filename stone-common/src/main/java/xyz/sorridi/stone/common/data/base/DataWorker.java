package xyz.sorridi.stone.common.data.base;

import xyz.sorridi.stone.common.data.base.op.DataAction;
import xyz.sorridi.stone.common.data.base.op.DataResult;
import xyz.sorridi.stone.common.threading.Pipeline;

import java.sql.Connection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A worker class responsible for performing database actions through a pipeline.
 * It manages the execution of read and write operations using a configurable number of threads.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class DataWorker
{
    private static final AtomicInteger ID = new AtomicInteger(0);

    private final DataOrigin origin;
    private final Pipeline pipeline;

    private final boolean startupWorker;

    /**
     * Constructs a DataWorker with default configuration for 1 read and 1 write thread.
     *
     * @param origin The data origin to interact with.
     */
    public DataWorker(DataOrigin origin)
    {
        this(origin, 1, 1, false);
    }

    /**
     * Constructs a DataWorker with default configuration for 1 read and 1 write thread.
     * Optionally starts the worker immediately.
     *
     * @param origin       The data origin to interact with.
     * @param startupWorker A flag indicating whether to start the worker immediately.
     */
    public DataWorker(DataOrigin origin, boolean startupWorker)
    {
        this(origin, 1, 1, startupWorker);
    }

    /**
     * Constructs a DataWorker with a specified number of read and write threads.
     *
     * @param origin        The data origin to interact with.
     * @param writeThreads  The number of threads to handle write operations.
     * @param readThreads   The number of threads to handle read operations.
     */
    public DataWorker(DataOrigin origin, int writeThreads, int readThreads)
    {
        this(origin, writeThreads, readThreads, false);
    }

    /**
     * Constructs a DataWorker with a specified number of read and write threads.
     * Optionally starts the worker immediately.
     *
     * @param origin        The data origin to interact with.
     * @param writeThreads  The number of threads to handle write operations.
     * @param readThreads   The number of threads to handle read operations.
     * @param startupWorker A flag indicating whether to start the worker immediately.
     */
    public DataWorker(DataOrigin origin, int writeThreads, int readThreads, boolean startupWorker)
    {
        this.origin = origin;
        this.startupWorker = startupWorker;
        this.pipeline = new Pipeline("data-" + ID.getAndIncrement(), readThreads, writeThreads);
    }

    /**
     * Submits a data result action to the pipeline and returns a future result.
     * The result is wrapped in an Optional to handle possible null values.
     *
     * @param action The data result action to execute.
     * @param type   The type of pipeline to use (read or write).
     * @param <T>    The type of the result returned by the action.
     * @return A CompletableFuture containing the result wrapped in an Optional.
     */
    public <T> CompletableFuture<Optional<T>> submit(DataResult<T> action, Pipeline.Types type)
    {
        CompletableFuture<Optional<T>> future = new CompletableFuture<>();

        CompletableFuture
                .runAsync(() ->
                {
                    waitUntilReady();

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
     * Submits a data action to the pipeline with no result and returns a future.
     *
     * @param action The data action to execute.
     * @param type   The type of pipeline to use (read or write).
     * @return A CompletableFuture representing the execution of the action.
     */
    public CompletableFuture<Void> submit(DataAction action, Pipeline.Types type)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();

        CompletableFuture
                .runAsync(() ->
                {
                    waitUntilReady();

                    try (Connection connection = origin.getConnection())
                    {
                        action.run(connection, origin);
                        future.complete(null);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        future.completeExceptionally(e);
                    }
                }, pipeline.get(type));

        return future;
    }

    /**
     * Shuts down the pipeline, stopping all operations.
     */
    public void shutdown()
    {
        pipeline.shutdown();
    }

    /**
     * Waits for the data origin to be ready before proceeding.
     * If the worker is not set to start up immediately, it waits until the origin signals readiness.
     */
    private void waitUntilReady()
    {
        if (!startupWorker && !origin.isReady())
        {
            origin.waitUntilReady();
        }
    }

}
