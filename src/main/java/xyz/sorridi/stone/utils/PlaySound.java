package xyz.sorridi.stone.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sound utilities.
 * @author Sorridi
 * @since 1.0
 */
public class PlaySound
{
    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     */
    public static void play(Player player, Sound sound)
    {
        play(player, sound, 1, 0, player.getLocation());
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     */
    public static void play(Player player, Sound sound, int volume)
    {
        play(player, sound, volume, 0, player.getLocation());
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     */
    public static void play(Player player, Sound sound, int volume, int pitch)
    {
        play(player, sound, volume, pitch, player.getLocation());
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param location  Location to play the sound at.
     */
    public static void play(Player player, Sound sound, Location location)
    {
        play(player, sound, 1, 0, location);
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param location  Location to play the sound at.
     */
    public static void play(Player player, Sound sound, int volume, int pitch, Location location)
    {
        checkNotNull(player, ErrorMessages.NULL.expected(Player.class));
        checkNotNull(sound, ErrorMessages.NULL.expected(Sound.class));
        checkNotNull(location, ErrorMessages.NULL.expected(Location.class));
        checkArgument(volume > 0, ErrorMessages.NEGATIVE.expected("volume"));
        checkArgument(pitch > 0, ErrorMessages.NEGATIVE.expected("pitch"));

        player.playSound(location, sound, volume, pitch);
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     * @param range     Range of the sound.
     */
    public static void playNearby(Entity entity, Sound sound, int range)
    {
        playNearby(entity, sound, 1, 0, range);
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param range     Range of the sound.
     */
    public static void playNearby(Entity entity, Sound sound, int volume, int pitch, int range)
    {
        playNearby(entity, sound, volume, pitch, range, entity.getLocation());
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     * @param range     Range of the sound.
     * @param location  Location to play the sound at.
     */
    public static void playNearby(Entity entity, Sound sound, int range, Location location)
    {
        playNearby(entity, sound, 1, 0, range, location);
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param range     Range of the sound.
     * @param location  Location to play the sound at.
     */
    public static void playNearby(Entity entity, Sound sound, int volume, int pitch, int range, Location location)
    {
        toPlayers(entity, range).forEach(e -> play((Player) e, sound, volume, pitch, location));
    }

    /**
     * Gets the players within a certain radius of an entity.
     * @param entity    Entity to get the players around.
     * @param range     Range to get the players in.
     * @return          Stream of players within the range.
     */
    private static Stream<Entity> toPlayers(Entity entity, int range)
    {
        return entity
                .getNearbyEntities(range, range, range)
                .stream()
                .filter(e -> e instanceof Player);
    }

}
