package xyz.sorridi.stone.spigot.utils.location;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import xyz.sorridi.stone.common.data.structures.SoftMap;
import xyz.sorridi.stone.common.immutable.Err;
import xyz.sorridi.stone.common.utils.data.Array;

/**
 * Evaluation of locations.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class LocationEvaluate
{
    public static final SoftMap<Array.Wrapper, Boolean> IS_NEAR_CACHE = new SoftMap<>();

    /**
     * Checks if two locations are equal in terms of X, Y, Z (ignoring yaw and pitch).
     *
     * @param location  Location to compare.
     * @param toCompare Location to compare.
     * @return If the locations are the same.
     */
    public static boolean isEqual(@NonNull Location location, @NonNull Location toCompare)
    {
        World world = location.getWorld();
        World _world = toCompare.getWorld();

        if (world != _world) {
            return false;
        }

        return location.getX() == toCompare.getX()
                && location.getY() == toCompare.getY()
                && location.getZ() == toCompare.getZ();
    }

    /**
     * Checks if two locations are similar in terms of X, Y, Z (ignoring yaw and pitch).
     *
     * @param location  Location to compare.
     * @param toCompare Location to compare.
     * @return If the locations are similar.
     */
    public static boolean isSimilar(@NonNull Location location, @NonNull Location toCompare)
    {
        World world = location.getWorld();
        World _world = toCompare.getWorld();

        if (world != _world) {
            return false;
        }

        return (int) location.getX() == (int) toCompare.getX()
                && (int) location.getY() == (int) toCompare.getY()
                && (int) location.getZ() == (int) toCompare.getZ();
    }

    /**
     * Gets the middle of a location.
     *
     * @param location The location to get the middle from.
     * @param yawPitch If the yaw and pitch should be the same as the original location.
     * @return The middle of the location.
     */
    public static Location getMiddleLocation(@NonNull Location location, boolean yawPitch)
    {
        World world = location.getWorld();

        double x = (int) location.getX() + 0.5;
        double z = (int) location.getZ() + 0.5;
        double y = location.getY();

        float yaw = yawPitch ? location.getYaw() : 0;
        float pitch = yawPitch ? location.getPitch() : 0;

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Gets the middle of a location (Y coordinate too).
     *
     * @param location The location to get the middle from.
     * @param yawPitch If the yaw and pitch should be the same as the original location.
     * @return The middle of the location.
     */
    public static Location getMiddleLocationFull(@NonNull Location location, boolean yawPitch)
    {
        World world = location.getWorld();

        double x = (int) location.getX() + 0.5;
        double z = (int) location.getZ() + 0.5;
        double y = (int) location.getY() + 0.5;

        float yaw = yawPitch ? location.getYaw() : 0;
        float pitch = yawPitch ? location.getPitch() : 0;

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Checks if a location is near another location.
     *
     * @param location     The location to check (dynamic preferred, for example the location of a player).
     * @param staticTarget The location to check (static preferred, for example the location of a block).
     * @param radius       The radius to check.
     * @return If the location is near the target.
     */
    public static boolean isNear(@NonNull Location location, @NonNull Location staticTarget, int radius)
    {
        return isNear(location, staticTarget, radius, false, false, false);
    }

    /**
     * Checks if a location is near another location.
     *
     * @param location       The location to check (dynamic preferred, for example the location of a player).
     * @param staticTarget   The location to check (static preferred, for example the location of a block).
     * @param radius         The radius to check.
     * @param exact          If the location should be exact.
     * @param shouldBeMiddle If the location should be the middle of the block (Y excluded).
     * @param middleFull     If the location should be the middle of the block (Y included).
     * @return If the location is near the target.
     */
    public static boolean isNear(@NonNull Location location,
                                 @NonNull Location staticTarget,
                                 int radius,
                                 boolean exact,
                                 boolean shouldBeMiddle,
                                 boolean middleFull)
    {
        Preconditions.checkArgument(radius > 0, Err.MUST_BE_POSITIVE.expect("radius"));

        if (shouldBeMiddle)
        {
            location = middleFull
                    ? getMiddleLocationFull(location, false)
                    : getMiddleLocation(location, false);
        }
        else if (!exact)
        {
            location.setX((int) location.getX());
            location.setY((int) location.getY());
            location.setZ((int) location.getZ());
        }

        if (!exact)
        {
            staticTarget.setX((int) staticTarget.getX());
            staticTarget.setY((int) staticTarget.getY());
            staticTarget.setZ((int) staticTarget.getZ());
        }

        var key = new Array.Wrapper(location, staticTarget);
        Boolean cached = IS_NEAR_CACHE.get(key);

        if (cached != null) {
            return cached;
        }

        boolean result = location.distance(staticTarget) <= radius;
        IS_NEAR_CACHE.put(key, result);

        return result;
    }

    /**
     * Gets the rough size of the cache.
     *
     * @return The rough size of the cache.
     */
    public static int getCacheSize()
    {
        return IS_NEAR_CACHE.size();
    }

}
