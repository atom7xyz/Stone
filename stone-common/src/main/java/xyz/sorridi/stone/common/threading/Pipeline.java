package xyz.sorridi.stone.common.threading;

import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.ExecutorService;

/**
 * Pipeline for executing tasks.
 * <p>
 * This class provides a way to separate tasks into two channels: read and write,
 * using the {@link Types} enum. Each type has its own thread pool.
 * </p>
 *
 * @author atom7xyz
 * @since 1.0
 */
@Getter
public class Pipeline
{
    private final Pool readPool, writePool;
    private boolean shutdown;

    /**
     * Creates a new pipeline with separate pools for read and write operations.
     *
     * @param name       The name of the pipeline.
     * @param readThreads The number of threads for the read pool.
     * @param writeThreads The number of threads for the write pool.
     */
    public Pipeline(@NonNull String name, int readThreads, int writeThreads)
    {
        readPool = new Pool("read-" + name, readThreads);
        writePool = new Pool("write-" + name, writeThreads);
    }

    /**
     * Gets the executor service for the specified pipeline type.
     *
     * @param type The pipeline type.
     * @return The executor service.
     */
    public ExecutorService get(@NonNull Types type)
    {
        return switch (type)
        {
            case READ -> readPool.getExecutor();
            case WRITE -> writePool.getExecutor();
        };
    }

    /**
     * Shuts down both read and write pools, marking the pipeline as shutdown.
     */
    public void shutdown()
    {
        readPool.shutdown();
        writePool.shutdown();
        shutdown = true;
    }

    /**
     * Provides a string representation of the pipeline.
     *
     * @return A string representation of the pipeline.
     */
    @Override
    public String toString()
    {
        return "Pipeline{" +
                "readPool=" + readPool +
                ", writePool=" + writePool +
                ", shutdown=" + shutdown +
                '}';
    }

    /**
     * Types of operation a pipeline can have.
     * <br>
     * <ul>
     * <li>{@link Types#READ}: Used for reading data from a source.</li>
     * <li>{@link Types#WRITE}: Used for writing data to a source.</li>
     * </ul>
     */
    public enum Types
    {
        READ,
        WRITE
    }
}
