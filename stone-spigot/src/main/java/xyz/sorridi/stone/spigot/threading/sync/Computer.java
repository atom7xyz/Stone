package xyz.sorridi.stone.spigot.threading.sync;

import me.lucko.helper.Schedulers;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A scheduler that executes tasks synchronously within a specified maximum amount of time per tick.
 * Tasks (computations) are queued and executed as long as there is time left in the current tick
 * (up to the configured `maxMsPerTick`).
 *
 * @author atom7xyz
 * @since 1.0
 */
public class Computer
{
    private static final int DEFAULT_MAX_MS_PER_TICK = 30;
    private final int maxMsPerTick;

    private final ConcurrentLinkedQueue<Computation> compQueue;

    /**
     * Constructs a Computer instance with the default maximum milliseconds allowed per tick.
     */
    public Computer()
    {
        this(DEFAULT_MAX_MS_PER_TICK);
    }

    /**
     * Constructs a Computer instance with a specified maximum milliseconds allowed per tick.
     *
     * @param maxMsPerTick Maximum time in milliseconds allowed per tick for executing tasks. Must be positive.
     * @throws IllegalArgumentException if `maxMsPerTick` is not positive.
     */
    public Computer(int maxMsPerTick)
    {
        checkArgument(maxMsPerTick > 0, Err.MUST_BE_POSITIVE.expect("maxMsPerTick"));
        this.maxMsPerTick = maxMsPerTick;

        compQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Adds a computation to the task queue for execution.
     *
     * @param computation The computation to add to the queue.
     */
    public void addComp(Computation computation)
    {
        compQueue.add(computation);
    }

    /**
     * Retrieves and removes the next computation from the queue.
     *
     * @return An {@code Optional} containing the next computation, or an empty {@code Optional} if the queue is empty.
     */
    public Optional<Computation> getComp()
    {
        return Optional.ofNullable(compQueue.poll());
    }

    /**
     * Starts processing computations from the queue. Computations are executed synchronously
     * without exceeding the configured time limit per tick (`maxMsPerTick`).
     * The execution is scheduled to run repeatedly at a fixed interval of 1 tick.
     */
    public void compute()
    {
        Runnable runnable = () ->
        {
            long stopTime = System.currentTimeMillis() + maxMsPerTick;

            while (!compQueue.isEmpty() && System.currentTimeMillis() <= stopTime)
            {
                getComp().ifPresent(Computation::execute);
            }
        };

        Schedulers.sync()
                .runRepeating(runnable, 1L, 1L);
    }

}
