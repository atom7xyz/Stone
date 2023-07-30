package xyz.sorridi.stone.common.threading;

import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.ExecutorService;

/**
 * Pipeline for executing tasks.
 * It gives a way to separate tasks in two canals using {@link Types}.
 *
 * @author Sorridi
 * @since 1.0
 */
@Getter
public class Pipeline
{
    private final Pool readPool, writePool;
    private boolean shutdown;

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
     * Shuts down the pipeline.
     */
    public void shutdown()
    {
        readPool.shutdown();
        writePool.shutdown();
        shutdown = true;
    }

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
     * Types of operation a pipeline has.
     * <br>
     * <br>
     * {@link Types#READ}: Used for reading data from a source.
     * {@link Types#WRITE}: Used for writing data to a source.
     */
    public enum Types
    {
        READ,
        WRITE
    }
}