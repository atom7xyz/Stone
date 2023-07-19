package xyz.sorridi.stone.utils;

import lombok.NonNull;
import me.lucko.helper.text3.Text;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import xyz.sorridi.stone.utils.data.Array;

import java.util.Collection;
import java.util.HashSet;

/**
 * Messaging utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Reply
{

    /**
     * Sends messages to a player.
     *
     * @param player   Player to send the message to.
     * @param messages Messages to send.
     * @return If the player is online.
     */
    public static boolean to(Player player, String... messages)
    {
        if (player == null || Array.isEmpty(messages))
        {
            return false;
        }

        if (!player.isOnline())
        {
            return false;
        }

        for (String message : messages)
        {
            if (!message.isEmpty())
            {
                player.sendMessage(Text.colorize(message));
            }
        }

        return true;
    }

    /**
     * Sends messages to a player.
     *
     * @param player     Player to send the message to.
     * @param collection Messages to send.
     * @return If the player is online.
     */
    public static <C extends Collection<String>> boolean to(Player player, C collection)
    {
        if (!player.isOnline() || Array.isEmpty(collection))
        {
            return false;
        }

        collection.stream()
                  .filter(message -> !message.isEmpty())
                  .forEach(message -> player.sendMessage(Text.colorize(message)));

        return true;
    }

    /**
     * Sends messages to a collection of players.
     *
     * @param players  The players to send the messages to.
     * @param messages The messages to send.
     * @param <P>      The collection type.
     */
    public static <P extends Collection<? extends Player>> void to(P players, String... messages)
    {
        players.forEach(player -> to(player, messages));
    }

    /**
     * Sends messages to a collection of players.
     *
     * @param players    The players to send the messages to.
     * @param collection The messages to send.
     * @param <P>        The collection type.
     * @param <C>        The messages collection type.
     */
    public static <P extends Collection<? extends Player>, C extends Collection<String>> void to(P players,
                                                                                                 C collection)
    {
        players.forEach(player -> to(player, collection));
    }

    /**
     * Sends messages to every online player.
     *
     * @param messages Messages to send.
     */
    public static void toAll(String... messages)
    {
        to(Players.all(), messages);
    }

    /**
     * Sends messages to every online player.
     *
     * @param collection Messages to send.
     */
    public static <P extends Collection<String>> void toAll(P collection)
    {
        to(Players.all(), collection);
    }

    /**
     * Sends messages to every online player except the specified one.
     *
     * @param exclude  Player to exclude.
     * @param messages Messages to send.
     */
    public static void toAllExcept(@NonNull Player exclude, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every online player except the specified one.
     *
     * @param toExclude  Player to exclude.
     * @param collection Messages to send.
     */
    public static <T extends Collection<String>> void toAllExcept(@NonNull Player toExclude, T collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.stream()
               .filter(p -> !p.equals(toExclude))
               .forEach(p -> to(p, collection));
    }

    /**
     * Sends messages to every online player except the specified ones.
     *
     * @param toExclude Players to exclude.
     * @param messages  Messages to send.
     * @param <P>       The collection type.
     */
    public static <P extends Collection<Player>> void toAllExcept(@NonNull P toExclude, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        HashSet<Player> excluded = new HashSet<>(toExclude);

        Players.stream()
               .filter(p -> !excluded.contains(p))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every online player except the specified ones.
     *
     * @param toExclude  Players to exclude.
     * @param collection Messages to send.
     * @param <P>        The collection type.
     * @param <C>        The collection type.
     */
    public static <P extends Collection<Player>, C extends Collection<String>> void toAllExcept(@NonNull P toExclude,
                                                                                                C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        HashSet<Player> excluded = new HashSet<>(toExclude);

        Players.stream()
               .filter(p -> !excluded.contains(p))
               .forEach(p -> to(p, collection));
    }

    /**
     * Sends messages to every player in the collection except the specified ones.
     *
     * @param players  Players to send the message to.
     * @param exclude  Players to exclude.
     * @param messages Messages to send.
     */
    public static <P extends Collection<Player>> void toExcept(@NonNull P players,
                                                               @NonNull P exclude,
                                                               String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        players.stream()
               .filter(p -> !exclude.contains(p))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every player in the collection except the specified ones.
     *
     * @param players    Players to send the message to.
     * @param exclude    Players to exclude.
     * @param collection Messages to send.
     */
    public static <P extends Collection<Player>, C extends Collection<String>> void toExcept(@NonNull P players,
                                                                                             @NonNull P exclude,
                                                                                             C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        players.stream()
               .filter(p -> !exclude.contains(p))
               .forEach(p -> to(p, collection));
    }

    /**
     * Sends messages to every player in the collection except the specified one.
     *
     * @param players  Players to send the message to.
     * @param exclude  Player to exclude.
     * @param messages Messages to send.
     */
    public static <T extends Collection<Player>> void toExcept(@NonNull T players,
                                                               @NonNull Player exclude,
                                                               String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        players.stream()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every player in the collection except the specified one.
     *
     * @param players    Players to send the message to.
     * @param exclude    Player to exclude.
     * @param collection Messages to send.
     */
    public static <T extends Collection<Player>, C extends Collection<String>> void toExcept(@NonNull T players,
                                                                                             @NonNull Player exclude,
                                                                                             C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        players.stream()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, collection));
    }

    /**
     * Sends messages to every player with the specified permission.
     *
     * @param permission Permission to check.
     * @param messages   Messages to send.
     */
    public static void toAllWithPerm(@NonNull String permission, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every player with the specified permission.
     *
     * @param permission Permission to check.
     * @param collection Messages to send.
     */
    public static <C extends Collection<String>> void toAllWithPerm(@NonNull String permission, C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission))
               .forEach(p -> to(p, collection));
    }

    /**
     * Sends messages to every player with the specified permission except the specified one.
     *
     * @param permission Permission to check.
     * @param exclude    Player to exclude.
     * @param messages   Messages to send.
     */
    public static void toAllWithPermExcept(@NonNull String permission, @NonNull Player exclude, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission) && !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every player with the specified permission except the specified ones.
     *
     * @param permission Permission to check.
     * @param exclude    Players to exclude.
     * @param messages   Messages to send.
     */
    public static <T extends Collection<Player>> void toAllWithPermExcept(@NonNull String permission,
                                                                          @NonNull T exclude,
                                                                          String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission) && !exclude.contains(p))
               .forEach(p -> to(p, messages));
    }

    /**
     * Sends messages to every player with the specified permission except the specified ones.
     *
     * @param permission Permission to check.
     * @param exclude    Players to exclude.
     * @param collection Messages to send.
     */
    public static <T extends Collection<Player>, C extends Collection<String>> void toAllWithPermExcept(@NonNull String permission,
                                                                                                        @NonNull T exclude,
                                                                                                        C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission) && !exclude.contains(p))
               .forEach(p -> to(p, collection));
    }

}
