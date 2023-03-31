package xyz.sorridi.stone.utils;

import me.lucko.helper.text3.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

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
    public static boolean to(Player player, String... messages)
    {
        if (player.isOnline())
        {
            for (String message : messages)
            {
                player.sendMessage(Text.colorize(message));
            }
        }

        return player.isOnline();
    }

    /**
     * Sends messages to a player.
     * @param player Player to send the message to.
     * @param collection Messages to send.
     * @return If the player is online.
     * @param <T> The collection type.
     */
    public static <T extends Collection<String>> boolean to(Player player, T collection)
    {
        return to(player, collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every online player.
     * @param messages Messages to send.
     */
    public static void toAll(String... messages)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            for (String message : messages)
            {
                p.sendMessage(Text.colorize(message));
            }
        }
    }

    /**
     * Sends messages to every online player.
     * @param collection Messages to send.
     * @param <T> The collection type.
     */
    public static <T extends Collection<String>> void toAll(T collection)
    {
       toAll(collection.toArray(new String[0]));
    }

    /**
     * Sends messages to every online player except the specified one.
     * @param player Player to exclude.
     * @param messages Messages to send.
     */
    public static void toAllExcept(Player player, String... messages)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p != player)
            {
                for (String message : messages)
                {
                    player.sendMessage(Text.colorize(message));
                }
            }
        }
    }

    /**
     * Sends messages to every online player except the specified one.
     * @param player Player to exclude.
     * @param collection Messages to send.
     * @param <T> The collection type.
     */
    public static <T extends Collection<String>> void toAllExcept(Player player, T collection)
    {
        toAllExcept(player, collection.toArray(new String[0]));
    }

}
