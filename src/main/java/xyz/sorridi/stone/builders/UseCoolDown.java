package xyz.sorridi.stone.builders;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class UseCoolDown
{
    private final HashMap<Player, Long> coolDowns;
    private final long time;

    /**
     * Constructor for a new UseCoolDown.
     * @param time Time between each use.
     * @param timeUnit Time unit.
     */
    public UseCoolDown(long time, TimeUnit timeUnit)
    {
        coolDowns = new HashMap<>();
        this.time = timeUnit.toMillis(time);
    }

    /**
     * Puts a player in the cool down.
     * @param player Player to be put in the cool down.
     */
    public void put(Player player)
    {
        checkNotNull(player, ErrorMessages.NULL.expected(Player.class));

        coolDowns.putIfAbsent(player, 0L);
    }

    /**
     * Renews the cool down for a player.
     * @param player The player.
     */
    public void renew(Player player)
    {
        checkNotNull(player, ErrorMessages.NULL.expected(Player.class));

        coolDowns.replace(player, System.currentTimeMillis());
    }

    /**
     * Removes a player from the cool down.
     * @param player Player to be removed.
     */
    public void remove(Player player)
    {
        checkNotNull(player, ErrorMessages.NULL.expected(Player.class));

        coolDowns.remove(player);
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
     * @param player Player to check.
     * @return The time difference.
     */
    public long timeDiff(Player player)
    {
        checkNotNull(player, ErrorMessages.NULL.expected(Player.class));

        return System.currentTimeMillis() - coolDowns.get(player);
    }

    /**
     * Retrieves the cool down left for a player.
     * @param player Player to check.
     * @return Time left in milliseconds.
     */
    public long usableIn(Player player)
    {
        return time - timeDiff(player);
    }

    /**
     * Retrieves the cool down left for a player.
     * @param player Player to check.
     * @param timeUnit Time unit to convert to.
     * @return Time left in the specified time unit.
     */
    public long usableIn(Player player, TimeUnit timeUnit)
    {
        return timeUnit.convert(usableIn(player), TimeUnit.MILLISECONDS);
    }

    /**
     * Checks if the cool down has expired.
     * @param player Player to check.
     * @return If the cool down has expired.
     */
    public boolean isUsable(Player player)
    {
        return timeDiff(player) >= time;
    }

}
