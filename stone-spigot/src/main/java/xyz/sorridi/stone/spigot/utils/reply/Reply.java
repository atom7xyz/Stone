package xyz.sorridi.stone.spigot.utils.reply;

import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Reply implementation for Spigot.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Reply
{
    private static final ReplyImpl IMPL = new ReplyImpl();

    public static boolean to(Player player, String... messages)
    {
        return IMPL.to(player, messages);
    }

    public static <C extends Collection<String>> boolean to(Player player, C collection)
    {
        return IMPL.to(player, collection);
    }

    public static void toAll(String... messages)
    {
        IMPL.toAll(messages);
    }

    public static <P extends Collection<String>> void toAll(P collection)
    {
        IMPL.toAll(collection);
    }

    public static void toAllExcept(Player exclude, String... messages)
    {
        IMPL.toAllExcept(exclude, messages);
    }

    public static <C extends Collection<String>> void toAllExcept(Player toExclude, C collection)
    {
        IMPL.toAllExcept(toExclude, collection);
    }

    public static <P extends Collection<Player>> void toAllExcept(P toExclude, String... messages)
    {
        IMPL.toAllExcept(toExclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<String>> void toAllExceptMulti(P toExclude,
                                                                                                     C collection)
    {
        IMPL.toAllExceptMulti(toExclude, collection);
    }

    public static <P extends Collection<Player>> void toExcept(P players, P exclude, String... messages)
    {
        IMPL.toExcept(players, exclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<String>> void toExcept(P players,
                                                                                             P exclude,
                                                                                             C collection)
    {
        IMPL.toExcept(players, exclude, collection);
    }

    public static <P extends Collection<Player>> void toExcept(P players, Player exclude, String... messages)
    {
        IMPL.toExcept(players, exclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<String>> void toExcept(P players,
                                                                                             Player exclude,
                                                                                             C collection)
    {
        IMPL.toExcept(players, exclude, collection);
    }

    public static void toAllWithPerm(String permission, String... messages)
    {
        IMPL.toAllWithPerm(permission, messages);
    }

    public static <C extends Collection<String>> void toAllWithPerm(String permission, C collection)
    {
        IMPL.toAllWithPerm(permission, collection);
    }

    public static void toAllWithPermExcept(String permission, Player exclude, String... messages)
    {
        IMPL.toAllWithPermExcept(permission, exclude, messages);
    }

    public static <P extends Collection<Player>> void toAllWithPermExcept(String permission,
                                                                          P exclude,
                                                                          String... messages)
    {
        IMPL.toAllWithPermExcept(permission, exclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<String>> void toAllWithPermExcept(String permission,
                                                                                                        P exclude,
                                                                                                        C collection)
    {
        IMPL.toAllWithPermExcept(permission, exclude, collection);
    }

}
