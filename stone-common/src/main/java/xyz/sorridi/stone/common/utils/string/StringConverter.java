package xyz.sorridi.stone.common.utils.string;

import lombok.NonNull;
import xyz.sorridi.stone.common.data.structures.SoftMap;
import xyz.sorridi.stone.common.immutable.Err;
import xyz.sorridi.stone.common.utils.data.Array;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * String conversion utilities.
 * <p>
 * This class provides methods to convert different types of data (e.g., time, strings, numbers) to various
 * human-readable formats, including converting milliseconds to human-readable time, converting numbers to Roman numerals,
 * and formatting strings like snake_case to camelCase or proper case.
 * </p>
 *
 * @author atom7xyz
 * @since 1.0
 */
public class StringConverter
{
    public static final SoftMap<Array.Wrapper, String> MILLS_TO_HUMAN_CACHE;
    public static final SoftMap<String, String> CAMELCASE_CACHE;
    public static final SoftMap<String, String> PROPERCASE_CACHE;
    public static final SoftMap<Integer, String> ROMAN_CACHE;

    private static final String DEFAULT_DATE_FORMAT = "HH:mm:ss dd/MM/yy";

    static
    {
        MILLS_TO_HUMAN_CACHE = new SoftMap<>();
        ROMAN_CACHE = new SoftMap<>();
        CAMELCASE_CACHE = new SoftMap<>();
        PROPERCASE_CACHE = new SoftMap<>();
    }

    /**
     * Converts a long to a human-readable format.
     * <p>
     * Converts a time in milliseconds into a readable string in the format of:
     * [days] [hours] [minutes] [seconds] [milliseconds].
     * The singular or plural form of each time unit is determined based on the value.
     * </p>
     *
     * @param plurals   The plurals of the time units.
     * @param singulars The singulars of the time units.
     * @param time      The time in milliseconds to convert.
     * @return The human-readable time string.
     */
    public static String fromMillisToHuman(@NonNull String[] plurals, @NonNull String[] singulars, long time)
    {
        checkArgument(plurals.length == 5, Err.INVALID_ARRAY_LENGTH.expect(5));
        checkArgument(singulars.length == 5, Err.INVALID_ARRAY_LENGTH.expect(5));

        var key = new Array.Wrapper(time, plurals, singulars);
        var val = MILLS_TO_HUMAN_CACHE.get(key);

        if (val != null) {
            return val;
        }

        long _days = TimeUnit.MILLISECONDS.toDays(time);
        long _hours = TimeUnit.MILLISECONDS.toHours(time);
        long _minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long _seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        long hours = _hours - TimeUnit.DAYS.toHours(_days);
        long minutes = _minutes - TimeUnit.HOURS.toMinutes(_hours);
        long seconds = _seconds - TimeUnit.MINUTES.toSeconds(_minutes);
        long millis = time - TimeUnit.SECONDS.toMillis(_seconds);

        StringBuilder builder = new StringBuilder();
        String result;

        boolean pending = false;

        if (_days > 0)
        {
            pending = true;
            builder.append(_days);

            if (_days < 2)
            {
                builder.append(singulars[0]);
            }
            else
            {
                builder.append(plurals[0]);
            }
        }

        if (hours > 0)
        {
            if (pending)
            {
                builder.append(" ");
            }

            pending = true;
            builder.append(hours);

            if (hours < 2)
            {
                builder.append(singulars[1]);
            }
            else
            {
                builder.append(plurals[1]);
            }
        }

        if (minutes > 0)
        {
            if (pending)
            {
                builder.append(" ");
            }

            pending = true;
            builder.append(minutes);

            if (minutes < 2)
            {
                builder.append(singulars[2]);
            }
            else
            {
                builder.append(plurals[2]);
            }
        }

        if (seconds > 0)
        {
            if (pending)
            {
                builder.append(" ");
            }

            builder.append(seconds);

            if (seconds < 2)
            {
                builder.append(singulars[3]);
            }
            else
            {
                builder.append(plurals[3]);
            }
        }

        if (millis > 0 && minutes == 0 && seconds == 0)
        {
            if (pending)
            {
                builder.append(" ");
            }

            builder.append(millis);

            if (millis < 2)
            {
                builder.append(singulars[4]);
            }
            else
            {
                builder.append(plurals[4]);
            }
        }

        result = builder.toString();
        MILLS_TO_HUMAN_CACHE.put(key, result);

        return result;
    }

    /**
     * Converts minutes to a time string in the format HH:mm.
     *
     * @param minutes The total number of minutes.
     * @return The converted time in HH:mm format.
     */
    public static String fromMinutesToHHmm(int minutes)
    {
        checkArgument(minutes > 0, Err.ZERO.get());

        long hours = TimeUnit.MINUTES.toHours(minutes);
        long hoursAsMinutes = TimeUnit.HOURS.toMinutes(hours);
        long remainMinutes = minutes - hoursAsMinutes;

        return String.format("%02dh %02dm", hours, remainMinutes);
    }

    /**
     * Extracts the first number found in the given string.
     *
     * @param string The string from which to extract the number.
     * @return The extracted number.
     * @throws NumberFormatException If no valid number is found.
     */
    public static long extractNumber(@NonNull String string) throws NumberFormatException
    {
        return Integer.parseInt(string.replaceAll("[^0-9]", ""));
    }

    /**
     * Converts a snake_case string to camelCase.
     *
     * @param string The string to convert.
     * @return The converted string in camelCase format.
     */
    public static String fromSneakToCamel(@NonNull String string)
    {
        var cached = CAMELCASE_CACHE.get(string);

        if (cached != null)
        {
            return cached;
        }

        String[] split = string.split("_");
        StringBuilder camel = new StringBuilder();
        String result;

        for (String part : split)
        {
            camel.append(toProperCase(part));
        }

        result = camel.toString();
        CAMELCASE_CACHE.put(string, result);

        return result;
    }

    /**
     * Converts the first letter of a string to uppercase and the rest to lowercase.
     *
     * @param string The string to convert.
     * @return The string in proper case.
     */
    public static String toProperCase(@NonNull String string)
    {
        var cached = PROPERCASE_CACHE.get(string);

        if (cached != null) {
            return cached;
        }

        String result = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        PROPERCASE_CACHE.put(string, result);

        return result;
    }

    /**
     * Converts the current system time to a string in HH:mm:ss dd/MM/yy format.
     *
     * @return The formatted current time.
     */
    public static String convertCurrentTime()
    {
        return convertTime(DEFAULT_DATE_FORMAT, System.currentTimeMillis());
    }

    /**
     * Converts the given time in milliseconds to a string in HH:mm:ss dd/MM/yy format.
     *
     * @param time The time in milliseconds.
     * @return The formatted time.
     */
    public static String convertTime(long time)
    {
        return convertTime(DEFAULT_DATE_FORMAT, time);
    }

    /**
     * Converts the given time in milliseconds to a string using a custom format.
     *
     * @param timeFormat The format to use for the conversion.
     * @param time       The time in milliseconds to convert.
     * @return The formatted time string.
     */
    public static String convertTime(@NonNull String timeFormat, long time)
    {
        Date date = new Date(time);
        Format format = new SimpleDateFormat(timeFormat);

        return format.format(date);
    }

    /**
     * Converts an integer to its Roman numeral equivalent.
     *
     * @param input The integer to convert.
     * @return The Roman numeral representation.
     */
    public static String toRoman(int input)
    {
        var cached = ROMAN_CACHE.get(input);

        if (cached != null)
        {
            return cached;
        }

        StringBuilder temp = new StringBuilder();
        String result;

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

        result = temp.toString();
        ROMAN_CACHE.put(input, result);

        return result;
    }
}
