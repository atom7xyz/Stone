package xyz.sorridi.stone.builders;

import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Getter
public class UseCoolDown<T>
{
    private final HashMap<T, Long> coolDowns;
    private final long time;

    /**
     * @param time Time between each use.
     * @param timeUnit Time unit.
     */
    public UseCoolDown(long time, TimeUnit timeUnit)
    {
        coolDowns = new HashMap<>();
        this.time = timeUnit.toMillis(time);
    }

    /**
     * Puts a target in the cool down.
     * @param target Target to be put in the cool down.
     */
    public void putIfAbsent(@NonNull T target)
    {
        coolDowns.putIfAbsent(target, 0L);
    }

    /**
     * Renews the cool down for a target.
     * @param target The target.
     */
    public void renew(@NonNull T target)
    {
        coolDowns.replace(target, System.currentTimeMillis());
    }

    /**
     * Removes a target from the cool down.
     * @param target Target to be removed.
     */
    public void remove(@NonNull T target)
    {
        coolDowns.remove(target);
    }

    /**
     * Clears cool downs.
     */
    public void clear()
    {
        coolDowns.clear();
    }

    /**
     * Checks the time difference between the current time and the players' last cool down entry.
     * @param target Target to check.
     * @return The time difference.
     */
    public long timeDiff(@NonNull T target)
    {
        return System.currentTimeMillis() - coolDowns.get(target);
    }

    /**
     * Retrieves the cool down left for a target.
     * @param target Target to check.
     * @return Time left in milliseconds.
     */
    public long usableIn(@NonNull T target)
    {
        return time - timeDiff(target);
    }

    /**
     * Retrieves the cool down left for a target.
     * @param target Target to check.
     * @param timeUnit Time unit to convert to.
     * @return Time left in the specified time unit.
     */
    public long usableIn(@NonNull T target, TimeUnit timeUnit)
    {
        return timeUnit.convert(usableIn(target), TimeUnit.MILLISECONDS);
    }

    /**
     * Checks if the cool down has expired.
     * @param target Target to check.
     * @return If the cool down has expired.
     */
    public boolean isUsable(@NonNull T target)
    {
        return timeDiff(target) >= time;
    }

}
