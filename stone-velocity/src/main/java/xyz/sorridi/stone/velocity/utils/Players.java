package xyz.sorridi.stone.velocity.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.velocity.StoneVelocity;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Players utility class for Velocity.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Players
{
    private static ProxyServer proxy;

    /**
     * Used only by the library itself to set the proxy instance.
     *
     * @param stoneVelocity The library instance.
     */
    public static void setServer(StoneVelocity stoneVelocity)
    {
        Players.proxy = stoneVelocity.getServer();
    }

    /**
     * Gets the player count of the entire proxy.
     *
     * @return The player count of the entire proxy.
     */
    public static int getCount()
    {
        return proxy.getPlayerCount();
    }

    /**
     * Gets the maximum player count of the entire proxy.
     *
     * @return The maximum player count of the entire proxy.
     */
    public static int getMaxCount()
    {
        return proxy.getConfiguration()
                    .getShowMaxPlayers();
    }

    /**
     * Gets the player count of a specific server.
     *
     * @param server The server to get the player count of.
     * @return The player count of the server.
     */
    public static Optional<Integer> getCountIn(String server)
    {
        return proxy.getServer(server)
                    .map(s -> s.getPlayersConnected().size());
    }

    /**
     * Gets the player count of a specific server.
     *
     * @param server The server to get the player count of.
     * @return The player count of the server.
     */
    public static int getCountInUnsafe(String server)
    {
        return getCountIn(server).orElse(0);
    }

    /**
     * Gets the player count of the server in which the player is in.
     *
     * @param player The player to get the server of.
     * @return The player count of the server.
     */
    public static Optional<Integer> getCountIn(Player player)
    {
        return player.getCurrentServer()
                     .map(s -> s.getServer().getPlayersConnected().size());
    }

    /**
     * Gets the player count of the server in which the player is in.
     *
     * @param player The player to get the server of.
     * @return The player count of the server.
     */
    public static int getCountInUnsafe(Player player)
    {
        return getCountIn(player).orElse(0);
    }

    /**
     * Gets all the players on the proxy.
     *
     * @return All the players on the proxy.
     */
    public static Collection<Player> getAll()
    {
        return proxy.getAllPlayers();
    }

    /**
     * Gets a stream of all the players on the proxy.
     *
     * @return A stream of all the players on the proxy.
     */
    public static Stream<Player> streamAll()
    {
        return getAll().stream();
    }

    /**
     * Gets a stream of all the players on the server.
     *
     * @param serverName The name of the server.
     * @return A stream of all the players on the server.
     */
    public static Optional<Stream<Player>> streamAllIn(String serverName)
    {
        return getPlayersIn(serverName).map(Collection::stream);
    }

    /**
     * Gets a stream of all the players on the server.
     *
     * @param serverName The name of the server.
     * @return A stream of all the players on the server.
     */
    public static Stream<Player> streamAllInUnsafe(String serverName)
    {
        return getPlayersIn(serverName).stream()
                                       .flatMap(Collection::stream);
    }

    /**
     * Gets a stream of all the players of the server in which the player is in.
     *
     * @param player The player to get the server of.
     * @return A stream of all the players on the server.
     */
    public static Optional<Stream<Player>> streamAllIn(Player player)
    {
        return player.getCurrentServer()
                     .map(s -> s.getServer().getPlayersConnected().stream());
    }

    /**
     * Gets a stream of all the players on a specific server.
     *
     * @param player The player to get the server of.
     * @return A stream of all the players on the server.
     */
    public static Stream<Player> streamAllInUnsafe(Player player)
    {
        return streamAllIn(player).orElse(Stream.empty());
    }

    /**
     * Gets a stream of all the players on a specific server.
     *
     * @param serverName The name of the server.
     * @return All the players on the server.
     */
    public static Optional<Collection<Player>> getPlayersIn(String serverName)
    {
        return proxy.getServer(serverName)
                    .map(RegisteredServer::getPlayersConnected);
    }

    /**
     * Gets a stream of all the players on a specific server.
     *
     * @param serverName The name of the server.
     * @return All the players on the server.
     */
    public static Collection<Player> gePlayersInUnsafe(String serverName)
    {
        return getPlayersIn(serverName).orElse(Collections.emptyList());
    }

    private static Stream<Player> streamAllWIthPredicate(Predicate<Player> predicate)
    {
        return streamAll().filter(predicate);
    }

    private static Collection<Player> getAllWithPredicate(Predicate<Player> predicate)
    {
        return streamAllWIthPredicate(predicate).collect(Collectors.toList());
    }

    /**
     * Gets all the players with a specific permission.
     *
     * @param permission The permission to check.
     * @return All the players with the permission.
     */
    public static Collection<Player> getAllWithPerm(String permission)
    {
        return getAllWithPredicate(player -> player.hasPermission(permission));
    }

    /**
     * Gets all the players without a specific permission.
     *
     * @param permission The permission to check.
     * @return All the players without the permission.
     */
    public static Collection<Player> getAllWithoutPerm(String permission)
    {
        return getAllWithPredicate(player -> !player.hasPermission(permission));
    }

    /**
     * Gets a stream of all the players with a specific permission.
     *
     * @param permission The permission to check.
     * @return A stream of all the players with the permission.
     */
    public static Stream<Player> streamAllWithPerm(String permission)
    {
        return streamAllWIthPredicate(player -> player.hasPermission(permission));
    }

    /**
     * Gets a stream of all the players without a specific permission.
     *
     * @param permission The permission to check.
     * @return A stream of all the players without the permission.
     */
    public static Stream<Player> streamAllWithoutPerm(String permission)
    {
        return streamAllWIthPredicate(player -> !player.hasPermission(permission));
    }

    /**
     * Gets a player by their name.
     *
     * @param name The name of the player.
     * @return The player.
     */
    public static Optional<Player> get(String name)
    {
        return proxy.getPlayer(name);
    }

    /**
     * Gets a player by their UUID.
     *
     * @param uuid The UUID of the player.
     * @return The player.
     */
    public static Optional<Player> get(UUID uuid)
    {
        return proxy.getPlayer(uuid);
    }

    /**
     * Gets a player by their name.
     *
     * @param name The name of the player.
     * @return The player if found, otherwise null.
     */
    public static @Nullable Player getUnsafe(String name)
    {
        return get(name).orElse(null);
    }

    /**
     * Gets a player by their UUID.
     *
     * @param uuid The UUID of the player.
     * @return The player if found, otherwise null.
     */
    public static @Nullable Player getUnsafe(UUID uuid)
    {
        return get(uuid).orElse(null);
    }

    /**
     * Checks if a player is online.
     *
     * @param name The name of the player.
     * @return If the player is online.
     */
    public boolean isOnline(String name)
    {
        return proxy.getPlayer(name).isPresent();
    }

    /**
     * Checks if a player is online.
     *
     * @param uuid The UUID of the player.
     * @return If the player is online.
     */
    public boolean isOnline(UUID uuid)
    {
        return proxy.getPlayer(uuid).isPresent();
    }

}
