package xyz.sorridi.stone.utils.location;

import org.bukkit.Location;
import org.bukkit.World;
import xyz.sorridi.stone.immutable.ErrorMessages;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Evaluation of locations.
 * @author Sorridi
 * @since 1.0
 */
public class LocationEvaluate
{
    /**
     * Checks if two locations are equal in terms of X, Y, Z (ignoring yaw and pitch).
     * @param location  Location to compare.
     * @param toCompare Location to compare.
     * @return If the locations are the same (true) or not (false).
     */
    public static boolean isEqual(Location location, Location toCompare)
    {
        checkNotNull(location, ErrorMessages.NULL.expect(Location.class));
        checkNotNull(toCompare, ErrorMessages.NULL.expect(Location.class));

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
     * Gets the middle of a location.
     * @param location  The location to get the middle from.
     * @param yawPitch  If the yaw and pitch should be the same as the original location (true) or not (false).
     * @return The middle of the location.
     */
    public static Location getMiddleLocation(Location location, boolean yawPitch)
    {
        checkNotNull(location, ErrorMessages.NULL.expect(Location.class));

        World world = location.getWorld();

        double x    = (int) location.getX() + .5;
        double z    = (int) location.getZ() + .5;
        double y    = location.getY();

        float yaw   = yawPitch ? location.getYaw() : 0;
        float pitch = yawPitch ? location.getPitch() : 0;

        return new Location(world, x, y, z, yaw, pitch);
    }

}
