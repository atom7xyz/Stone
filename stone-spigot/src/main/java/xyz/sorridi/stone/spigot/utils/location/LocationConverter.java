package xyz.sorridi.stone.spigot.utils.location;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Conversion of locations.
 *
 * @author Sorridi
 * @since 1.0
 */
public class LocationConverter
{

    /**
     * Formats objects to a string like x:y:z.
     *
     * @param object The objects to format.
     * @return The formatted objects.
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
     * Extracts the X, Y, Z of a location in a String, using X:Y:Z format.
     *
     * @param location The location to extract from.
     * @return The extracted string.
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
     * Converts the Location into a String, using WORLD:X:Y:Z:YAW:PITCH format.
     *
     * @param location The location to convert from.
     * @return The location converted.
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
     * Converts a String into a Location, using WORLD:X:Y:Z:YAW:PITCH format.
     *
     * @param stringLocation The string to convert from.
     * @param yawPitch       If yaw and pitch should be included.
     * @return The converted Location.
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
