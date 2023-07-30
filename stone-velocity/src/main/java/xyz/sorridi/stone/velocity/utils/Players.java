package xyz.sorridi.stone.velocity.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.velocity.StoneVelocity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Players utility class for Velocity.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Players
{
    private static StoneVelocity server;
    private static ProxyServer proxy;

    /**
     * Used only by the library itself to set the server instance.
     * @param server The server instance.
     */
    public static void setServer(StoneVelocity server)
    {
        Players.server = server;
        Players.proxy = server.getServer();
    }

    /**
     * Gets a stream of all the players on the server.
     * @return A stream of all the players on the server.
     */
    public static Stream<Player> stream()
    {
        return proxy.getAllPlayers()
                    .stream();
    }

    /**
     * Gets a player by their name.
     * @param name The name of the player.
     * @return The player if found, otherwise null.
     */
    public static @Nullable Player getUnsafe(String name)
    {
        return proxy.getPlayer(name)
                    .orElse(null);
    }

    /**
     * Gets a player by their UUID.
     * @param uuid The UUID of the player.
     * @return The player if found, otherwise null.
     */
    public static @Nullable Player getUnsafe(UUID uuid)
    {
        return proxy.getPlayer(uuid)
                    .orElse(null);
    }

    /**
     * Gets a player by their name.
     * @param name The name of the player.
     * @return The player.
     */
    public static Optional<Player> get(String name)
    {
        return proxy.getPlayer(name);
    }

    /**
     * Gets a player by their UUID.
     * @param uuid The UUID of the player.
     * @return The player.
     */
    public static Optional<Player> get(UUID uuid)
    {
        return proxy.getPlayer(uuid);
    }

    /**
     * Checks if a player is online.
     * @param name The name of the player.
     * @return If the player is online.
     */
    public boolean isOnline(String name)
    {
        return proxy.getPlayer(name).isPresent();
    }

    /**
     * Checks if a player is online.
     * @param uuid The UUID of the player.
     * @return If the player is online.
     */
    public boolean isOnline(UUID uuid)
    {
        return proxy.getPlayer(uuid).isPresent();
    }

}
