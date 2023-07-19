package xyz.sorridi.stone.utils.string;

import xyz.sorridi.stone.immutable.Err;

import java.text.DecimalFormat;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * String formatting utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class StringFormatter
{
    private static final DecimalFormat SINGLE_DIGIT_FORMAT = new DecimalFormat("0.0");
    private static final DecimalFormat DOUBLE_DIGIT_FORMAT = new DecimalFormat("0.00");
    private static final DecimalFormat TRIPLE_DIGIT_FORMAT = new DecimalFormat("0.000");
    private static final DecimalFormat QUAD_DIGIT_FORMAT = new DecimalFormat("0.0000");
    private static final DecimalFormat QUINT_DIGIT_FORMAT = new DecimalFormat("0.00000");

    /**
     * Formats a double to a single digit.
     *
     * @param value    The value to format.
     * @param decimals The number of decimals.
     * @return The formatted value.
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

    private static String formatCustom(double value, int decimals)
    {
        checkArgument(decimals > 0, Err.ZERO.get());

        return new DecimalFormat("0." + "0".repeat(decimals)).format(value);
    }

    /**
     * Formats a double to a single digit.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    public static String formatSingle(double value)
    {
        return SINGLE_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to a double digit.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    public static String formatDouble(double value)
    {
        return DOUBLE_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to a triple digit.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    public static String formatTriple(double value)
    {
        return TRIPLE_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to a quadruple digit.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    public static String formatQuadruple(double value)
    {
        return QUAD_DIGIT_FORMAT.format(value);
    }

    /**
     * Formats a double to a quintuple digit.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    public static String formatQuintuple(double value)
    {
        return QUINT_DIGIT_FORMAT.format(value);
    }

}
