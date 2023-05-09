package xyz.sorridi.stone.utils;

import lombok.NonNull;
import me.lucko.helper.text3.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

/**
 * Messaging utilities.
 * @author Sorridi
 * @since 1.0
 */
public class Reply
{

    /**
     * Sends messages to a player.
     * @param player Player to send the message to.
     * @param messages Messages to send.
     * @return If the player is online.
     */
    public static boolean to(@NonNull Player player, String... messages)
    {
        if (player.isOnline())
        {
            for (String message : messages)
            {
                if (!message.isEmpty())
                {
                    player.sendMessage(Text.colorize(message));
                }
            }
        }

        return player.isOnline();
    }

    /**
     * Sends messages to a player.
     * @param player Player to send the message to.
     * @param collection Messages to send.
     * @return If the player is online.
     */
    public static <C extends Collection<String>> boolean to(@NonNull Player player, @NonNull C collection)
    {
        return to(player, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every online player.
     * @param messages Messages to send.
     */
    public static void toAll(String... messages)
    {
        toAll(Bukkit.getOnlinePlayers(), messages);
    }

    /**
     * Sends messages to every online player.
     * @param collection Messages to send.
     */
    public static <P extends Collection<String>> void toAll(@NonNull P collection)
    {
       toAll(collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every player in the collection.
     * @param players Players to send the message to.
     * @param messages Messages to send.
     */
    public static <P extends Collection<? extends Player>> void toAll(@NonNull P players, String... messages)
    {
        for (Player p : players)
        {
           to(p, messages);
        }
    }

    /**
     * Sends messages to every player in the collection.
     * @param players Players to send the message to.
     * @param collection Messages to send.
     */
    public static <P extends Collection<Player>, C extends Collection<String>>
    void toAll(@NonNull P players, C collection)
    {
        toAll(players, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every online player except the specified one.
     * @param player Player to exclude.
     * @param messages Messages to send.
     */
    public static void toAllExcept(@NonNull Player player, String... messages)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (!p.equals(player))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every online player except the specified one.
     * @param player Player to exclude.
     * @param collection Messages to send.
     */
    public static <T extends Collection<String>> void toAllExcept(@NonNull Player player, @NonNull T collection)
    {
        toAllExcept(player, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every online player except the specified ones.
     * @param toExclude Players to exclude.
     * @param messages Messages to send.
     * @param <T> The collection type.
     */
    public static <T extends Collection<Player>> void toAllExcept(@NonNull T toExclude, String... messages)
    {
        HashSet<Player> excluded = new HashSet<>(toExclude);

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (!excluded.contains(p))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every online player except the specified ones.
     * @param toExclude Players to exclude.
     * @param collection Messages to send.
     * @param <P> The collection type.
     * @param <C> The collection type.
     */
    public static <P extends Collection<Player>, C extends Collection<String>>
    void toAllExcept(@NonNull P toExclude, @NonNull C collection)
    {
        toAllExcept(toExclude, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every player in the collection except the specified ones.
     * @param players Players to send the message to.
     * @param exclude Players to exclude.
     * @param messages Messages to send.
     */
    public static <P extends Collection<Player>>
    void toAllExcept(@NonNull P players, @NonNull P exclude, String... messages)
    {
        for (Player p : players)
        {
            if (!exclude.contains(p))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every player in the collection except the specified ones.
     * @param players Players to send the message to.
     * @param exclude Players to exclude.
     * @param collection Messages to send.
     */
    public static <P extends Collection<Player>, C extends Collection<String>>
    void toAllExcept(@NonNull P players, @NonNull P exclude, @NonNull C collection)
    {
        toAllExcept(players, exclude, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every player in the collection except the specified one.
     * @param players Players to send the message to.
     * @param exclude Player to exclude.
     * @param messages Messages to send.
     */
    public static <T extends Collection<Player>>
    void toAllExcept(@NonNull T players, @NonNull Player exclude, String... messages)
    {
        for (Player p : players)
        {
            if (!p.equals(exclude))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every player in the collection except the specified one.
     * @param players Players to send the message to.
     * @param exclude Player to exclude.
     * @param collection Messages to send.
     */
    public static <T extends Collection<Player>, C extends Collection<String>>
    void toAllExcept(@NonNull T players, @NonNull Player exclude, @NonNull C collection)
    {
        toAllExcept(players, exclude, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every player with the specified permission.
     * @param permission Permission to check.
     * @param messages Messages to send.
     */
    public static void toAllWithPerm(@NonNull String permission, String... messages)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.hasPermission(permission))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every player with the specified permission.
     * @param permission Permission to check.
     * @param collection Messages to send.
     */
    public static <C extends Collection<String>> void toAllWithPerm(@NonNull String permission, @NonNull C collection)
    {
        toAllWithPerm(permission, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every player with the specified permission except the specified one.
     * @param permission Permission to check.
     * @param exclude Player to exclude.
     * @param messages Messages to send.
     */
    public static void toAllWithPermExcept(@NonNull String permission, @NonNull Player exclude, String... messages)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.hasPermission(permission) && !p.equals(exclude))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every player with the specified permission except the specified ones.
     * @param permission Permission to check.
     * @param exclude Players to exclude.
     * @param messages Messages to send.
     */
    public static <T extends Collection<Player>>
    void toAllWithPermExcept(@NonNull String permission, @NonNull T exclude, String... messages)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.hasPermission(permission) && !exclude.contains(p))
            {
                to(p, messages);
            }
        }
    }

    /**
     * Sends messages to every player with the specified permission except the specified ones.
     * @param permission Permission to check.
     * @param exclude Players to exclude.
     * @param collection Messages to send.
     */
    public static <T extends Collection<Player>, C extends Collection<String>>
    void toAllWithPermExcept(@NonNull String permission, @NonNull T exclude, @NonNull C collection)
    {
        toAllWithPermExcept(permission, exclude, collection.toArray(new String[0]));
    }


}
