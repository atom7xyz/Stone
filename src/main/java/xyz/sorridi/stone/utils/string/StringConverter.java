package xyz.sorridi.stone.utils.string;

import org.bukkit.ChatColor;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

public class StringConverter
{

    /**
     * Converts minutes to HH:mm format.
     * @param minutes The minutes to convert.
     * @return The converted minutes.
     */
    public static String fromMinutesToHHmm(int minutes)
    {
        checkArgument(minutes > 0, ErrorMessages.ZERO);

        long hours          = TimeUnit.MINUTES.toHours(minutes);
        long hoursAsMinutes = TimeUnit.HOURS.toMinutes(hours);
        long remainMinutes  = minutes - hoursAsMinutes;

        return String.format("%02dh %02dm", hours, remainMinutes);
    }

    /**
     * Strips the color from a string.
     * @param string The string to strip.
     * @return The stripped string.
     */
    public static String stripColor(String string)
    {
        return ChatColor.stripColor(string);
    }

    /**
     * Extracts a number from a string.
     * @param string The string to extract from.
     * @return The extracted number.
     */
    public static long extractNumber(String string) throws NumberFormatException
    {
        return Integer.parseInt(stripColor(string).replaceAll("[^0-9]", ""));
    }

    /**
     * Converts a sneak-case String to a camel-case String.
     * @param string The string to convert.
     * @return The converted string.
     */
    public static String toCamelCase(String string)
    {
        String[] split      = string.split("_");
        StringBuilder camel = new StringBuilder();

        for (String part : split)
        {
            camel.append(toProperCase(part));
        }

        return camel.toString();
    }

    /**
     * Sets the first letter of a string to uppercase.
     * @param string The string to convert.
     * @return The converted string.
     */
    public static String toProperCase(String string)
    {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    /**
     * Converts the current time into a String, using HH:mm:ss dd/MM/yy format.
     * @return The converted time.
     */
    public static String convertCurrentTime()
    {
        return convertTime("HH:mm:ss dd/MM/yy", System.currentTimeMillis());
    }

    /**
     * Converts the given time into a String, using HH:mm:ss dd/MM/yy format.
     * @param time The time to convert.
     * @return The converted time.
     */
    public static String convertTime(long time)
    {
        return convertTime("HH:mm:ss dd/MM/yy", time);
    }

    /**
     * Converts the given time into a String, using the specified format.
     * @param timeFormat The format to use.
     * @return The converted time.
     */
    public static String convertTime(String timeFormat, long time)
    {
        Date date       = new Date(time);
        Format format   = new SimpleDateFormat(timeFormat);

        return format.format(date);
    }

    /**
     * Converts a number into Roman numerals.
     * @param input The number to convert.
     * @return The converted number.
     */
    public static String toRoman(int input)
    {
        StringBuilder temp = new StringBuilder();

        while (input >= 1000)
        {
            temp.append("M");
            input -= 1000;
        }
        while (input >= 900)
        {
            temp.append("CM");
            input -= 900;
        }
        while (input >= 500)
        {
            temp.append("D");
            input -= 500;
        }
        while (input >= 400)
        {
            temp.append("CD");
            input -= 400;
        }
        while (input >= 100)
        {
            temp.append("C");
            input -= 100;
        }
        while (input >= 90)
        {
            temp.append("XC");
            input -= 90;
        }
        while (input >= 50)
        {
            temp.append("L");
            input -= 50;
        }
        while (input >= 40)
        {
            temp.append("XL");
            input -= 40;
        }
        while (input >= 10)
        {
            temp.append("X");
            input -= 10;
        }
        while (input >= 9)
        {
            temp.append("IX");
            input -= 9;
        }
        while (input >= 5)
        {
            temp.append("V");
            input -= 5;
        }
        while (input >= 4)
        {
            temp.append("IV");
            input -= 4;
        }
        while (input >= 1)
        {
            temp.append("I");
            input -= 1;
        }

        return temp.toString();
    }

}
