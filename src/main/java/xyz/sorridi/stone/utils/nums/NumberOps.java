package xyz.sorridi.stone.utils.nums;

import xyz.sorridi.stone.utils.data.Array;

/**
 * Number utilities.
 * @since 1.0
 * @author Sorridi
 */
public class NumberOps
{
    public static final String[] SUFFIXES = Array.of("", "k", "M", "G", "T", "P", "E", "Z", "Y");

    /**
     * Safely divides two numbers.
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of the division.
     */
    public static double safeDiv(double x, double y)
    {
        double res = x / y;

        if (Double.isNaN(res) || Double.isInfinite(res))
        {
            return x;
        }

        return res;
    }

    /**
     * Shortens a number.
     * @param x The number to shorten.
     * @return The shortened number.
     */
    public static String shorten(double x)
    {
        int exp = (int) (Math.log10(x) / 3);
        double num = x / Math.pow(10, exp * 3);

        return String.format("%.1f%s", num, SUFFIXES[exp]);
    }

}
