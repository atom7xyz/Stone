package xyz.sorridi.stone.spigot.utils;

import lombok.NonNull;
import me.lucko.helper.utils.Players;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Utility class for playing sounds to players or nearby entities.
 * Provides multiple overloaded methods for flexibility in specifying sound volume, pitch, location, and range.
 * Ensures proper input validation for parameters such as volume and pitch.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class PlaySound
{

    /**
     * Plays a sound to a player with default volume and pitch at the player's current location.
     *
     * @param player The player to play the sound to.
     * @param sound  The sound to play.
     */
    public static void play(@NonNull Player player, @NonNull Sound sound)
    {
        play(player, sound, 1, 1, player.getLocation());
    }

    /**
     * Plays a sound to a player with a specified volume and default pitch at the player's current location.
     *
     * @param player The player to play the sound to.
     * @param sound  The sound to play.
     * @param volume The volume of the sound (must be non-negative).
     */
    public static void play(@NonNull Player player, @NonNull Sound sound, int volume)
    {
        play(player, sound, volume, 1, player.getLocation());
    }

    /**
     * Plays a sound to a player with specified volume and pitch at the player's current location.
     *
     * @param player The player to play the sound to.
     * @param sound  The sound to play.
     * @param volume The volume of the sound (must be non-negative).
     * @param pitch  The pitch of the sound (must be non-negative).
     */
    public static void play(@NonNull Player player, @NonNull Sound sound, int volume, int pitch)
    {
        play(player, sound, volume, pitch, player.getLocation());
    }

    /**
     * Plays a sound to a player at a specific location with default volume and pitch.
     *
     * @param player   The player to play the sound to.
     * @param sound    The sound to play.
     * @param location The location to play the sound at.
     */
    public static void play(@NonNull Player player,
                            @NonNull Sound sound,
                            @NonNull Location location)
    {
        play(player, sound, 1, 1, location);
    }

    /**
     * Plays a sound to a player with specified volume, pitch, and location.
     *
     * @param player   The player to play the sound to.
     * @param sound    The sound to play.
     * @param volume   The volume of the sound (must be non-negative).
     * @param pitch    The pitch of the sound (must be non-negative).
     * @param location The location to play the sound at.
     */
    public static void play(@NonNull Player player,
                            @NonNull Sound sound,
                            int volume,
                            int pitch,
                            @NonNull Location location)
    {
        checkArgument(volume >= 0, Err.NEGATIVE.expect("volume"));
        checkArgument(pitch >= 0, Err.NEGATIVE.expect("pitch"));

        player.playSound(location, sound, volume, pitch);
    }

    /**
     * Plays a sound to all players within a certain range of an entity.
     * Default volume and pitch are used.
     *
     * @param entity The entity used as the center of the range.
     * @param sound  The sound to play.
     * @param range  The range (radius) within which players will hear the sound.
     */
    public static void playNearby(@NonNull Entity entity, @NonNull Sound sound, int range)
    {
        playNearby(entity, sound, 1, 1, range);
    }

    /**
     * Plays a sound to all players within a certain range of an entity with specified volume and pitch.
     *
     * @param entity The entity used as the center of the range.
     * @param sound  The sound to play.
     * @param volume The volume of the sound (must be non-negative).
     * @param pitch  The pitch of the sound (must be non-negative).
     * @param range  The range (radius) within which players will hear the sound.
     */
    public static void playNearby(@NonNull Entity entity, @NonNull Sound sound, int volume, int pitch, int range)
    {
        playNearby(entity, sound, volume, pitch, range, entity.getLocation());
    }

    /**
     * Plays a sound to all players within a certain range of an entity at a specific location.
     * Default volume and pitch are used.
     *
     * @param entity   The entity used as the center of the range.
     * @param sound    The sound to play.
     * @param range    The range (radius) within which players will hear the sound.
     * @param location The location where the sound will be played.
     */
    public static void playNearby(@NonNull Entity entity, @NonNull Sound sound, int range, @NonNull Location location)
    {
        playNearby(entity, sound, 1, 1, range, location);
    }

    /**
     * Plays a sound to all players within a certain range of an entity at a specific location,
     * with specified volume and pitch.
     *
     * @param entity   The entity used as the center of the range.
     * @param sound    The sound to play.
     * @param volume   The volume of the sound (must be non-negative).
     * @param pitch    The pitch of the sound (must be non-negative).
     * @param range    The range (radius) within which players will hear the sound.
     * @param location The location where the sound will be played.
     */
    public static void playNearby(@NonNull Entity entity,
                                  @NonNull Sound sound,
                                  int volume,
                                  int pitch,
                                  int range,
                                  @NonNull Location location)
    {
        toPlayers(entity, range).forEach(p -> play(p, sound, volume, pitch, location));
    }

    /**
     * Retrieves all players within a certain radius of a given entity.
     *
     * @param entity The entity used as the center of the range.
     * @param radius The radius within which to find players.
     * @return A stream of players within the given radius.
     */
    private static Stream<Player> toPlayers(Entity entity, int radius)
    {
        return Players.streamInRange(entity.getLocation(), radius);
    }

}
