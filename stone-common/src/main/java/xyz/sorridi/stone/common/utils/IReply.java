package xyz.sorridi.stone.common.utils;

import lombok.NonNull;

import java.util.Collection;

/**
 * Interface for sending messages to players, with support for excluding certain players,
 * sending messages to players with specific permissions, and working with collections of players or messages.
 *
 * @param <T> The type representing a player.
 * @param <R> The type representing a message.
 *
 * @author atom7xyz
 * @since 1.0
 */
public interface IReply<T, R>
{

    /**
     * Sends one or more messages to a single player.
     *
     * @param player   The player to send the messages to.
     * @param messages The messages to send.
     * @return True if the player is online, false otherwise.
     */
    boolean to(T player, R... messages);

    /**
     * Sends a collection of messages to a single player.
     *
     * @param player     The player to send the messages to.
     * @param collection The collection of messages to send.
     * @return True if the player is online, false otherwise.
     */
    <C extends Collection<R>> boolean to(T player, C collection);

    /**
     * Sends one or more messages to all players in a collection.
     *
     * @param players  The collection of players to send messages to.
     * @param messages The messages to send.
     * @param <P>      The type of the player collection.
     */
    default <P extends Collection<? extends T>> void to(P players, R... messages) {
        players.forEach(player -> to(player, messages));
    }

    /**
     * Sends a collection of messages to all players in a collection.
     *
     * @param players    The collection of players to send messages to.
     * @param collection The collection of messages to send.
     * @param <P>        The type of the player collection.
     * @param <C>        The type of the message collection.
     */
    default <P extends Collection<? extends T>, C extends Collection<R>> void to(P players, C collection) {
        players.forEach(player -> to(player, collection));
    }

    /**
     * Sends one or more messages to all online players.
     *
     * @param messages The messages to send.
     */
    void toAll(R... messages);

    /**
     * Sends a collection of messages to all online players.
     *
     * @param collection The collection of messages to send.
     */
    <C extends Collection<R>> void toAll(C collection);

    /**
     * Sends one or more messages to all online players, excluding a specified player.
     *
     * @param exclude  The player to exclude from receiving the messages.
     * @param messages The messages to send.
     */
    void toAllExcept(@NonNull T exclude, R... messages);

    /**
     * Sends a collection of messages to all online players, excluding a specified player.
     *
     * @param toExclude  The player to exclude from receiving the messages.
     * @param collection The collection of messages to send.
     */
    <C extends Collection<R>> void toAllExcept(@NonNull T toExclude, C collection);

    /**
     * Sends one or more messages to all online players, excluding a collection of specified players.
     *
     * @param toExclude The collection of players to exclude.
     * @param messages  The messages to send.
     * @param <P>       The type of the player collection.
     */
    <P extends Collection<T>> void toAllExcept(@NonNull P toExclude, R... messages);

    /**
     * Sends a collection of messages to all online players, excluding a collection of specified players.
     *
     * @param toExclude  The collection of players to exclude.
     * @param collection The collection of messages to send.
     * @param <P>        The type of the player collection.
     * @param <C>        The type of the message collection.
     */
    <P extends Collection<T>, C extends Collection<R>> void toAllExceptMulti(@NonNull P toExclude, C collection);

    /**
     * Sends one or more messages to a collection of players, excluding a specified player.
     *
     * @param players  The collection of players to send messages to.
     * @param exclude  The player to exclude.
     * @param messages The messages to send.
     * @param <P>      The type of the player collection.
     */
    <P extends Collection<T>> void toExcept(P players, @NonNull T exclude, R... messages);

    /**
     * Sends a collection of messages to a collection of players, excluding a specified player.
     *
     * @param players    The collection of players to send messages to.
     * @param exclude    The player to exclude.
     * @param collection The collection of messages to send.
     * @param <P>        The type of the player collection.
     * @param <C>        The type of the message collection.
     */
    <P extends Collection<T>, C extends Collection<R>> void toExcept(P players, @NonNull T exclude, C collection);

    /**
     * Sends one or more messages to a collection of players, excluding a collection of specified players.
     *
     * @param players  The collection of players to send messages to.
     * @param exclude  The collection of players to exclude.
     * @param messages The messages to send.
     * @param <P>      The type of the player collection.
     */
    <P extends Collection<T>> void toExcept(P players, @NonNull P exclude, R... messages);

    /**
     * Sends a collection of messages to a collection of players, excluding a collection of specified players.
     *
     * @param players    The collection of players to send messages to.
     * @param exclude    The collection of players to exclude.
     * @param collection The collection of messages to send.
     * @param <P>        The type of the player collection.
     * @param <C>        The type of the message collection.
     */
    <P extends Collection<T>, C extends Collection<R>> void toExcept(P players, @NonNull P exclude, C collection);

    /**
     * Sends one or more messages to all players with a specific permission.
     *
     * @param permission The required permission.
     * @param messages   The messages to send.
     */
    void toAllWithPerm(@NonNull String permission, R... messages);

    /**
     * Sends a collection of messages to all players with a specific permission.
     *
     * @param permission The required permission.
     * @param collection The collection of messages to send.
     */
    <C extends Collection<R>> void toAllWithPerm(@NonNull String permission, C collection);

    /**
     * Sends one or more messages to all players with a specific permission, excluding a specified player.
     *
     * @param permission The required permission.
     * @param exclude    The player to exclude.
     * @param messages   The messages to send.
     */
    void toAllWithPermExcept(@NonNull String permission, @NonNull T exclude, R... messages);

    /**
     * Sends one or more messages to all players with a specific permission, excluding a collection of specified players.
     *
     * @param permission The required permission.
     * @param exclude    The collection of players to exclude.
     * @param messages   The messages to send.
     * @param <P>        The type of the player collection.
     */
    <P extends Collection<T>> void toAllWithPermExcept(@NonNull String permission, @NonNull P exclude, R... messages);

    /**
     * Sends a collection of messages to all players with a specific permission, excluding a collection of specified players.
     *
     * @param permission The required permission.
     * @param exclude    The collection of players to exclude.
     * @param collection The collection of messages to send.
     * @param <P>        The type of the player collection.
     * @param <C>        The type of the message collection.
     */
    <P extends Collection<T>, C extends Collection<R>> void toAllWithPermExcept(@NonNull String permission, @NonNull P exclude, C collection);

}
