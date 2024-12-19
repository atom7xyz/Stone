package xyz.sorridi.stone.common.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.NonNull;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A fixed & named thread pool.
 * <p>
 * This class provides a thread pool with a fixed number of threads. It allows for named thread pools
 * and can create thread pools with a number of threads equal to the number of processors or a specified number.
 * </p>
 *
 * @author atom7xyz
 * @since 1.0
 */
@Getter
public class Pool implements Executor
{
    // Atomic counter for generating unique pool IDs
    private static final AtomicInteger ID = new AtomicInteger(0);

    private final ExecutorService executor;
    private final ThreadFactory threadFactory;

    private final String poolName;
    private final int numThreads;

    /**
     * Creates a new generic pool with the number of threads equal to the number of processors.
     */
    public Pool()
    {
        this(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Creates a new generic pool with the given number of threads.
     *
     * @param numThreads The number of threads in the pool.
     */
    public Pool(int numThreads)
    {
        this("generic-" + ID.getAndIncrement(), numThreads);
    }

    /**
     * Creates a new pool with the specified name and the number of threads equal to the number of processors.
     *
     * @param poolName The name of the pool.
     */
    public Pool(@NonNull String poolName)
    {
        this(poolName, Runtime.getRuntime().availableProcessors());
    }

    /**
     * Creates a new pool with the given name and number of threads.
     *
     * @param poolName The name of the pool.
     * @param numThreads The number of threads in the pool.
     */
    public Pool(@NonNull String poolName, int numThreads)
    {
        checkArgument(numThreads > 0, Err.MUST_BE_POSITIVE.expect("threads"));

        this.numThreads = numThreads;
        this.poolName = poolName;
        this.threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName + " (#%d)")
                .build();
        this.executor = Executors.newFixedThreadPool(numThreads, threadFactory);
    }

    /**
     * Executes a given task in the pool.
     *
     * @param command The task to execute.
     */
    @Override
    public void execute(@NonNull Runnable command)
    {
        executor.execute(command);
    }

    /**
     * Provides a string representation of the Pool object.
     *
     * @return A string representation of the pool.
     */
    @Override
    public String toString()
    {
        return "Pool{" +
                "executor=" + executor +
                ", threadFactory=" + threadFactory +
                ", poolName='" + poolName + '\'' +
                ", numThreads=" + numThreads +
                '}';
    }

    /**
     * Shuts down the pool and releases resources.
     */
    public void shutdown()
    {
        executor.shutdown();
    }

}
