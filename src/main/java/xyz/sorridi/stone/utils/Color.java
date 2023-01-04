package xyz.sorridi.stone.utils;

import org.bukkit.ChatColor;

public class Color
{

    public static String me(String message, Object ...args)
    {
        return ChatColor.translateAlternateColorCodes('&', String.format(message, args));
    }

}
