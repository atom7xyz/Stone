package xyz.sorridi.stone.utils.bukkit.location;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.WeakHashMap;

/**
 * Evaluation of locations.
 * @author Sorridi
 * @since 1.0
 */
public class LocationEvaluate
{
    private static final
            WeakHashMap<Location,
            WeakHashMap<Location, Boolean>> IS_NEAR_CACHE;

    static
    {
        IS_NEAR_CACHE = new WeakHashMap<>();
    }

    /**
     * Checks if two locations are equal in terms of X, Y, Z (ignoring yaw and pitch).
     * @param location  Location to compare.
     * @param toCompare Location to compare.
     * @return If the locations are the same.
     */
    public static boolean isEqual(@NonNull Location location, @NonNull Location toCompare)
    {
        World world     = location.getWorld();
        World _world    = toCompare.getWorld();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double _x = toCompare.getX();
        double _y = toCompare.getY();
        double _z = toCompare.getZ();

        return (world == _world) && (x == _x) && (y == _y) && (z == _z);
    }

    /**
     * Checks if two locations are similar in terms of X, Y, Z (ignoring yaw and pitch).
     * @param location  Location to compare.
     * @param toCompare Location to compare.
     * @return If the locations are similar.
     */
    public static boolean isSimilar(@NonNull Location location, @NonNull Location toCompare)
    {
        World world     = location.getWorld();
        World _world    = toCompare.getWorld();

        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();

        int _x = (int) toCompare.getX();
        int _y = (int) toCompare.getY();
        int _z = (int) toCompare.getZ();

        return (world == _world) && (x == _x) && (y == _y) && (z == _z);
    }

    /**
     * Gets the middle of a location.
     * @param location  The location to get the middle from.
     * @param yawPitch  If the yaw and pitch should be the same as the original location.
     * @return The middle of the location.
     */
    public static Location getMiddleLocation(@NonNull Location location, boolean yawPitch)
    {
        World world = location.getWorld();

        double x    = (int) location.getX() + .5;
        double z    = (int) location.getZ() + .5;
        double y    = location.getY();

        float yaw   = yawPitch ? location.getYaw() : 0;
        float pitch = yawPitch ? location.getPitch() : 0;

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Gets the middle of a location (Y coordinate too).
     * @param location  The location to get the middle from.
     * @param yawPitch  If the yaw and pitch should be the same as the original location.
     * @return The middle of the location.
     */
    public static Location getMiddleLocationFull(@NonNull Location location, boolean yawPitch)
    {
        World world = location.getWorld();

        double x    = (int) location.getX() + .5;
        double z    = (int) location.getZ() + .5;
        double y    = (int) location.getY() + .5;

        float yaw   = yawPitch ? location.getYaw() : 0;
        float pitch = yawPitch ? location.getPitch() : 0;

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Checks if a location is near another location.
     * @param location The location to check (dynamic preferred, for example the location of a player).
     * @param staticTarget The location to check (static preferred, for example the location of a block).
     * @param radius The radius to check.
     * @return If the location is near the target.
     */
    public static boolean isNear(@NonNull Location location, @NonNull Location staticTarget, int radius)
    {
        return isNear(location, staticTarget, radius, false, false, false);
    }

    /**
     * Checks if a location is near another location.
     * @param location The location to check (dynamic preferred, for example the location of a player).
     * @param staticTarget The location to check (static preferred, for example the location of a block).
     * @param radius The radius to check.
     * @param exact If the location should be exact.
     * @param shouldBeMiddle If the location should be the middle of the block (Y excluded).
     * @param middleFull If the location should be the middle of the block (Y included).
     * @return If the location is near the target.
     */
    public static boolean isNear(@NonNull Location location,
                                 @NonNull Location staticTarget,
                                 int radius,
                                 boolean exact,
                                 boolean shouldBeMiddle,
                                 boolean middleFull
    ) {
        Preconditions.checkArgument(radius > 0, "Radius must be greater than 0.");

        if (shouldBeMiddle)
        {
            if (middleFull)
            {
                location = getMiddleLocationFull(location, false);
            }
            else
            {
                location = getMiddleLocation(location, false);
            }
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

        if (IS_NEAR_CACHE.containsKey(staticTarget))
        {
            val _map = IS_NEAR_CACHE.get(staticTarget);

            if (_map.containsKey(location))
            {
                return _map.get(location);
            }
        }

        boolean result = location.distance(staticTarget) <= radius;

        IS_NEAR_CACHE
                .computeIfAbsent(staticTarget, k -> new WeakHashMap<>())
                .putIfAbsent(location, result);

        return result;
    }

}
