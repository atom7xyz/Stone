package xyz.sorridi.stone.utils;

import org.bukkit.ChatColor;

/**
 * Color utilities.
 * @author Sorridi
 * @since 1.0
 */
public class Color
{

    /**
     * Converts a string to a colored string.
     * @param message The string to convert.
     * @param args The arguments to replace.
     * @return The converted string.
     */
    public static String me(String message, Object ...args)
    {
        return ChatColor.translateAlternateColorCodes('&', String.format(message, args));
    }

}
