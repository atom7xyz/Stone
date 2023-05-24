package xyz.sorridi.stone.utils.location;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;

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
     * @return If the locations are similar (true) or not (false).
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
     * @param yawPitch  If the yaw and pitch should be the same as the original location (true) or not (false).
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

}
