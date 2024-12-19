package xyz.sorridi.stone.common.utils.string;

import xyz.sorridi.stone.common.immutable.Err;

import java.text.DecimalFormat;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * String formatting utilities.
 * <p>
 * This class provides various utilities to format double values into strings with different decimal precisions.
 * It supports formatting the values to a specified number of decimal places (from 1 to 5 digits).
 *
 * @author atom7xyz
 * @since 1.0
 */
public class StringFormatter
{
    private static final DecimalFormat SINGLE_DIGIT_FORMAT  = new DecimalFormat("0.0");
    private static final DecimalFormat DOUBLE_DIGIT_FORMAT  = new DecimalFormat("0.00");
    private static final DecimalFormat TRIPLE_DIGIT_FORMAT  = new DecimalFormat("0.000");
    private static final DecimalFormat QUAD_DIGIT_FORMAT    = new DecimalFormat("0.0000");
    private static final DecimalFormat QUINT_DIGIT_FORMAT   = new DecimalFormat("0.00000");

    /**
     * Formats a double to a specified number of decimals.
     * <p>
     * This method selects an appropriate formatting based on the number of decimals provided.
     * It supports 1 to 5 decimals, and for custom decimal places, it uses the {@link #formatCustom(double, int)} method.
     *
     * @param value    The value to format.
     * @param decimals The number of decimals to round to.
     * @return The formatted value as a string.
     */
    public static String format(double value, int decimals)
    {
        return switch (decimals)
        {
            case 1 -> formatSingle(value);
            case 2 -> formatDouble(value);
            case 3 -> formatTriple(value);
            case 4 -> formatQuadruple(value);
            case 5 -> formatQuintuple(value);
            default -> formatCustom(value, decimals);
        };
    }

    /**
     * Formats a double to a custom number of decimals.
     * <p>
     * This method is used when the number of decimals is outside the 1 to 5 range. It dynamically generates a format
     * string based on the provided number of decimals.
     *
     * @param value    The value to format.
     * @param decimals The number of decimals to round to.
     * @return The formatted value as a string.
     */
    private static String formatCustom(double value, int decimals)
    {
        checkArgument(decimals > 0, Err.ZERO.get());

        return new DecimalFormat("0." + "0".repeat(decimals)).format(value);
    }

    /**
     * Formats a double to a single digit (1 decimal place).
     *
     * @param value The value to format.
     * @return The formatted value as a string.
     */
    public static String formatSingle(double value)
    {
        return SINGLE_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to two digits (2 decimal places).
     *
     * @param value The value to format.
     * @return The formatted value as a string.
     */
    public static String formatDouble(double value)
    {
        return DOUBLE_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to three digits (3 decimal places).
     *
     * @param value The value to format.
     * @return The formatted value as a string.
     */
    public static String formatTriple(double value)
    {
        return TRIPLE_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to four digits (4 decimal places).
     *
     * @param value The value to format.
     * @return The formatted value as a string.
     */
    public static String formatQuadruple(double value)
    {
        return QUAD_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to five digits (5 decimal places).
     *
     * @param value The value to format.
     * @return The formatted value as a string.
     */
    public static String formatQuintuple(double value)
    {
        return QUINT_DIGIT_FORMAT.format(value);
    }
}
