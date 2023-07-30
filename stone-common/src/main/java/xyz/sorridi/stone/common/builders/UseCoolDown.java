package xyz.sorridi.stone.common.builders;

import lombok.Getter;
import lombok.NonNull;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Cool downs for a target.
 * @param <T> Target type.
 *
 * @author Sorridi
 * @since 1.0
 */
@Getter
public class UseCoolDown<T> extends HashMap<T, Long>
{
    private final long usableEvery;

    /**
     * @param usableEvery Time between each use.
     * @param timeUnit    Time unit.
     */
    public UseCoolDown(long usableEvery, TimeUnit timeUnit)
    {
        checkArgument(usableEvery >= 0, Err.NEGATIVE.expect("usableEvery"));

        this.usableEvery = timeUnit.toMillis(usableEvery);
    }

    /**
     * Puts a target in the cool down.
     *
     * @param target Target to be put in the cool down.
     */
    public void create(@NonNull T target)
    {
        this.putIfAbsent(target, 0L);
    }

    /**
     * Refreshes the cool down for a target.
     *
     * @param target The target.
     */
    public void refresh(@NonNull T target)
    {
        this.replace(target, System.currentTimeMillis());
    }

    /**
     * Checks the time difference between the current time and the players' last cool down entry.
     *
     * @param target Target to check.
     * @return The time difference.
     */
    public long timeDiff(@NonNull T target)
    {
        return System.currentTimeMillis() - this.get(target);
    }

    /**
     * Retrieves the cool down left for a target.
     *
     * @param target Target to check.
     * @return Time left in milliseconds.
     */
    public long usableIn(@NonNull T target)
    {
        return usableEvery - timeDiff(target);
    }

    /**
     * Retrieves the cool down left for a target.
     *
     * @param target   Target to check.
     * @param timeUnit Time unit to convert to.
     * @return Time left in the specified time unit.
     */
    public long usableIn(@NonNull T target, TimeUnit timeUnit)
    {
        return timeUnit.convert(usableIn(target), TimeUnit.MILLISECONDS);
    }

    /**
     * Checks if the cool down has expired.
     *
     * @param target Target to check.
     * @return If the cool down has expired.
     */
    public boolean isUsable(@NonNull T target)
    {
        return timeDiff(target) >= usableEvery;
    }

}
