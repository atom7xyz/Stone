package xyz.sorridi.stone.spigot.threading.sync;

import me.lucko.helper.Schedulers;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A computer that executes tasks synchronously without exceeding a maximum amount of time per tick.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Computer
{
    private static final int DEFAULT_MAX_MS_PER_TICK = 30;
    private final int maxMsPerTick;

    private final ConcurrentLinkedQueue<Computation> compQueue;

    public Computer()
    {
        this(DEFAULT_MAX_MS_PER_TICK);
    }

    public Computer(int maxMsPerTick)
    {
        checkArgument(maxMsPerTick > 0, Err.MUST_BE_POSITIVE.expect("maxMsPerTick"));
        this.maxMsPerTick = maxMsPerTick;

        compQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Adds a computation to the queue.
     *
     * @param computation Computation to add.
     */
    public void addComp(Computation computation)
    {
        compQueue.add(computation);
    }

    /**
     * Retrieves a computation from the queue.
     *
     * @return The computation.
     */
    public Optional<Computation> getComp()
    {
        return Optional.ofNullable(compQueue.poll());
    }

    /**
     * Computes the computations in the queue.
     */
    public void compute()
    {
        Schedulers.sync()
                  .runRepeating(() ->
                                {
                                    long stopTime = System.currentTimeMillis() + maxMsPerTick;

                                    while (!compQueue.isEmpty() && System.currentTimeMillis() <= stopTime)
                                    {
                                        getComp().ifPresent(Computation::execute);
                                    }
                                }, 1L, 1L);
    }

}
