package xyz.sorridi.stone.common.utils;

import lombok.NonNull;

import java.util.Collection;

public interface IReply<T>
{

    /**
     * Sends messages to a player.
     *
     * @param player   Player to send the message to.
     * @param messages Messages to send.
     * @return If the player is online.
     */
    boolean to(T player, String... messages);

    /**
     * Sends messages to a player.
     *
     * @param player     Player to send the message to.
     * @param collection Messages to send.
     * @return If the player is online.
     */
    <C extends Collection<String>> boolean to(T player, C collection);

    /**
     * Sends messages to a collection of players.
     *
     * @param players  The players to send the messages to.
     * @param messages The messages to send.
     * @param <P>      The collection type.
     */
    default <P extends Collection<? extends T>> void to(P players, String... messages)
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
    default <P extends Collection<? extends T>, C extends Collection<String>> void to(P players, C collection)
    {
        players.forEach(player -> to(player, collection));
    }

    /**
     * Sends messages to every online player.
     *
     * @param messages Messages to send.
     */
    void toAll(String... messages);

    /**
     * Sends messages to every online player.
     *
     * @param collection Messages to send.
     */
    <P extends Collection<String>> void toAll(P collection);

    /**
     * Sends messages to every online player except the specified one.
     *
     * @param exclude  Player to exclude.
     * @param messages Messages to send.
     */
    void toAllExcept(@NonNull T exclude, String... messages);

    /**
     * Sends messages to every online player except the specified one.
     *
     * @param toExclude  Player to exclude.
     * @param collection Messages to send.
     */
    <C extends Collection<String>> void toAllExcept(@NonNull T toExclude, C collection);

    /**
     * Sends messages to every online player except the specified ones.
     *
     * @param toExclude Players to exclude.
     * @param messages  Messages to send.
     * @param <P>       The collection type.
     */
    <P extends Collection<T>> void toAllExcept(@NonNull P toExclude, String... messages);

    /**
     * Sends messages to every online player except the specified ones.
     *
     * @param toExclude  Players to exclude.
     * @param collection Messages to send.
     * @param <P>        The collection type.
     * @param <C>        The collection type.
     */
    <P extends Collection<T>, C extends Collection<String>> void toAllExceptMulti(@NonNull P toExclude,
                                                                                  C collection);

    /**
     * Sends messages to every player in the collection except the specified ones.
     *
     * @param players  Players to send the message to.
     * @param exclude  Players to exclude.
     * @param messages Messages to send.
     */
    <P extends Collection<T>> void toExcept(P players, @NonNull P exclude, String... messages);

    /**
     * Sends messages to every player in the collection except the specified ones.
     *
     * @param players    Players to send the message to.
     * @param exclude    Players to exclude.
     * @param collection Messages to send.
     */
    <P extends Collection<T>, C extends Collection<String>> void toExcept(P players,
                                                                          @NonNull P exclude,
                                                                          C collection);

    /**
     * Sends messages to every player in the collection except the specified one.
     *
     * @param players  Players to send the message to.
     * @param exclude  Player to exclude.
     * @param messages Messages to send.
     */
    <P extends Collection<T>> void toExcept(P players, @NonNull T exclude, String... messages);

    /**
     * Sends messages to every player in the collection except the specified one.
     *
     * @param players    Players to send the message to.
     * @param exclude    Player to exclude.
     * @param collection Messages to send.
     */
    <P extends Collection<T>, C extends Collection<String>> void toExcept(P players,
                                                                          @NonNull T exclude,
                                                                          C collection);

    /**
     * Sends messages to every player with the specified permission.
     *
     * @param permission Permission to check.
     * @param messages   Messages to send.
     */
    void toAllWithPerm(@NonNull String permission, String... messages);

    /**
     * Sends messages to every player with the specified permission.
     *
     * @param permission Permission to check.
     * @param collection Messages to send.
     */
    <C extends Collection<String>> void toAllWithPerm(@NonNull String permission, C collection);

    /**
     * Sends messages to every player with the specified permission except the specified one.
     *
     * @param permission Permission to check.
     * @param exclude    Player to exclude.
     * @param messages   Messages to send.
     */
    void toAllWithPermExcept(@NonNull String permission, @NonNull T exclude, String... messages);

    /**
     * Sends messages to every player with the specified permission except the specified ones.
     *
     * @param permission Permission to check.
     * @param exclude    Players to exclude.
     * @param messages   Messages to send.
     */
    <P extends Collection<T>> void toAllWithPermExcept(@NonNull String permission,
                                                       @NonNull P exclude,
                                                       String... messages);

    /**
     * Sends messages to every player with the specified permission except the specified ones.
     *
     * @param permission Permission to check.
     * @param exclude    Players to exclude.
     * @param collection Messages to send.
     */
    <P extends Collection<T>, C extends Collection<String>> void toAllWithPermExcept(@NonNull String permission,
                                                                                     @NonNull P exclude,
                                                                                     C collection);

}
