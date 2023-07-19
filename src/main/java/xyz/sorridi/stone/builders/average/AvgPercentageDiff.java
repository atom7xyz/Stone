package xyz.sorridi.stone.builders.average;

import java.util.LinkedList;

/**
 * Class used to calculate the average of a given set of numbers.
 *
 * @author Sorridi
 * @since 1.0
 */
public class AvgPercentageDiff
{
    private final LinkedList<Double> firsts, seconds;

    public AvgPercentageDiff()
    {
        this.firsts = new LinkedList<>();
        this.seconds = new LinkedList<>();
    }

    /**
     * Calculates the average of the values in the history.
     *
     * @return The average of the values in the history.
     */
    public double calculate()
    {
        int num = size();
        double diff = 0.0;

        for (int i = 0; i < num; i++)
        {
            double firstVal = firsts.get(i);
            double secondVal = seconds.get(i);

            diff += ((secondVal - firstVal) / firstVal) * 100;
        }

        return diff / num;
    }

    /**
     * Adds a new value to the first list of values.
     *
     * @param v The value to add.
     */
    public void addFirst(double v)
    {
        firsts.add(v);
    }

    /**
     * Adds a new value to the second list of values.
     *
     * @param v The value to add.
     */
    public void addSecond(double v)
    {
        seconds.add(v);
    }

    /**
     * Adds new values to the first and second list of values.
     *
     * @param first  The value to add to the first list.
     * @param second The value to add to the second list.
     */
    public void add(double first, double second)
    {
        addFirst(first);
        addSecond(second);
    }

    /**
     * Clears the history.
     */
    public void clear()
    {
        firsts.clear();
        seconds.clear();
    }

    /**
     * Gets the (minimum) size of the history.
     *
     * @return The size of the history.
     */
    public int size()
    {
        return Math.min(firsts.size(), seconds.size());
    }
}
