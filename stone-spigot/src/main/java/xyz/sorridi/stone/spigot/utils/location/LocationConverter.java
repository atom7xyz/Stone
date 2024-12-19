package xyz.sorridi.stone.spigot.utils.location;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Utility class for location conversions.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class LocationConverter
{

    /**
     * Concatenates objects into a string using the format x:y:z.
     *
     * @param object The objects to concatenate.
     * @return A string representation of the concatenated objects.
     */
    public static String concat(Object... object)
    {
        StringBuilder temp = new StringBuilder();
        int objectsNum = object.length;

        for (int i = 0; i < objectsNum; i++)
        {
            temp.append(object[i]);

            if (i + 1 != objectsNum)
            {
                temp.append(":");
            }
        }

        return temp.toString();
    }

    /**
     * Extracts the X, Y, and Z coordinates from a location as a string
     * in the format X:Y:Z, with optional precision and yaw/pitch values.
     *
     * @param location The location to extract coordinates from.
     * @param precise  Whether to use precise decimal values.
     * @param yawPitch Whether to include yaw and pitch in the output.
     * @return A string containing the extracted coordinates.
     */
    public static String extractString(@NonNull Location location, boolean precise, boolean yawPitch)
    {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        if (yawPitch)
        {
            float yaw = location.getYaw();
            float pitch = location.getPitch();

            return precise ? concat(x, y, z, yaw, pitch)
                    : concat((int) x, (int) y, (int) z, (int) yaw, (int) pitch);
        }

        return precise ? concat(x, y, z)
                : concat((int) x, (int) y, (int) z);
    }

    /**
     * Converts a Location object into a string using the format
     * WORLD:X:Y:Z:YAW:PITCH.
     *
     * @param location The location to convert to a string.
     * @return A string representation of the location.
     */
    public static String getStringFromLocation(@NonNull Location location)
    {
        String worldName = location.getWorld().getName();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        float yaw = location.getYaw();
        float pitch = location.getPitch();

        return concat(worldName, x, y, z, yaw, pitch);
    }

    /**
     * Converts a string in the format WORLD:X:Y:Z:YAW:PITCH into a Location
     * object. The yaw and pitch values are optional based on the yawPitch flag.
     *
     * @param stringLocation The string representation of the location.
     * @param yawPitch       Whether yaw and pitch values are included.
     * @return A Location object parsed from the string.
     */
    public static Location getLocationFromString(@NonNull String stringLocation, boolean yawPitch)
    {
        String[] parts = stringLocation.split(":");

        World world = Bukkit.getServer().getWorld(parts[0]);

        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);

        float yaw = yawPitch ? Float.parseFloat(parts[4]) : 0;
        float pitch = yawPitch ? Float.parseFloat(parts[5]) : 0;

        return new Location(world, x, y, z, yaw, pitch);
    }

}
