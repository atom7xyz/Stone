package xyz.sorridi.stone.utils.string;

import xyz.sorridi.stone.immutable.ErrorMessages;

import java.text.DecimalFormat;

import static com.google.common.base.Preconditions.checkArgument;

public class StringFormatter
{
    private static final DecimalFormat singleDigitFormat    = new DecimalFormat("0.0");
    private static final DecimalFormat doubleDigitFormat    = new DecimalFormat("0.00");
    private static final DecimalFormat tripleDigitFormat    = new DecimalFormat("0.000");
    private static final DecimalFormat quadrupleDigitFormat = new DecimalFormat("0.0000");
    private static final DecimalFormat quintupleDigitFormat = new DecimalFormat("0.00000");

    public static String format(double value, int decimals)
    {
        return switch (decimals) {
            case 1  -> formatSingle(value);
            case 2  -> formatDouble(value);
            case 3  -> formatTriple(value);
            case 4  -> formatQuadruple(value);
            case 5  -> formatQuintuple(value);
            default -> formatCustom(value, decimals);
        };
    }

    private static String formatCustom(double value, int decimals)
    {
        checkArgument(decimals > 0, ErrorMessages.ZERO);

        return new DecimalFormat("0." + "0".repeat(decimals)).format(value);
    }

    public static String formatSingle(double value)
    {
        return singleDigitFormat.format(value);
    }

    public static String formatDouble(double value)
    {
        return doubleDigitFormat.format(value);
    }

    public static String formatTriple(double value)
    {
        return tripleDigitFormat.format(value);
    }

    public static String formatQuadruple(double value)
    {
        return quadrupleDigitFormat.format(value);
    }

    public static String formatQuintuple(double value)
    {
        return quintupleDigitFormat.format(value);
    }

}
