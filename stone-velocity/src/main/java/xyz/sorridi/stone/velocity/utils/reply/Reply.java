package xyz.sorridi.stone.velocity.utils.reply;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.TextComponent;
import xyz.sorridi.stone.common.utils.IReply;
import xyz.sorridi.stone.velocity.StoneVelocity;

import java.util.Collection;

/**
 * {@link IReply} implementation for Velocity.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Reply
{
    private static ReplyImpl IMPL;

    public static void setServer(StoneVelocity server)
    {
        IMPL = new ReplyImpl(server);
    }

    public static boolean to(Player player, TextComponent... messages)
    {
        return IMPL.to(player, messages);
    }

    public static <C extends Collection<TextComponent>> boolean to(Player player, C collection)
    {
        return IMPL.to(player, collection);
    }

    public static void toAll(TextComponent... messages)
    {
        IMPL.toAll(messages);
    }

    public static <P extends Collection<TextComponent>> void toAll(P collection)
    {
        IMPL.toAll(collection);
    }

    public static void toAllExcept(Player exclude, TextComponent... messages)
    {
        IMPL.toAllExcept(exclude, messages);
    }

    public static <C extends Collection<TextComponent>> void toAllExcept(Player toExclude, C collection)
    {
        IMPL.toAllExcept(toExclude, collection);
    }

    public static <P extends Collection<Player>> void toAllExcept(P toExclude, TextComponent... messages)
    {
        IMPL.toAllExcept(toExclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<TextComponent>> void toAllExceptMulti(P toExclude,
                                                                                                            C collection)
    {
        IMPL.toAllExceptMulti(toExclude, collection);
    }

    public static <P extends Collection<Player>> void toExcept(P players, P exclude, TextComponent... messages)
    {
        IMPL.toExcept(players, exclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<TextComponent>> void toExcept(P players,
                                                                                                    P exclude,
                                                                                                    C collection)
    {
        IMPL.toExcept(players, exclude, collection);
    }

    public static <P extends Collection<Player>> void toExcept(P players, Player exclude, TextComponent... messages)
    {
        IMPL.toExcept(players, exclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<TextComponent>> void toExcept(P players,
                                                                                                    Player exclude,
                                                                                                    C collection)
    {
        IMPL.toExcept(players, exclude, collection);
    }

    public static void toAllWithPerm(String permission, TextComponent... messages)
    {
        IMPL.toAllWithPerm(permission, messages);
    }

    public static <C extends Collection<TextComponent>> void toAllWithPerm(String permission, C collection)
    {
        IMPL.toAllWithPerm(permission, collection);
    }

    public static void toAllWithPermExcept(String permission, Player exclude, TextComponent... messages)
    {
        IMPL.toAllWithPermExcept(permission, exclude, messages);
    }

    public static <P extends Collection<Player>> void toAllWithPermExcept(String permission,
                                                                          P exclude,
                                                                          TextComponent... messages)
    {
        IMPL.toAllWithPermExcept(permission, exclude, messages);
    }

    public static <P extends Collection<Player>, C extends Collection<TextComponent>> void toAllWithPermExcept(String permission,
                                                                                                               P exclude,
                                                                                                               C collection)
    {
        IMPL.toAllWithPermExcept(permission, exclude, collection);
    }

}
