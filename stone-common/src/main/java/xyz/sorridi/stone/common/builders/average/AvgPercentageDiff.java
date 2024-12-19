package xyz.sorridi.stone.common.builders.average;

import java.util.LinkedList;

/**
 * Class used to calculate the average percentage difference between two sets of numbers.
 * The calculation is based on the formula: ((second value - first value) / first value) * 100.
 *
 * @author atom7xyz
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
     * Calculates the average percentage difference between corresponding values in the history lists.
     * The calculation is based on the formula: ((second value - first value) / first value) * 100.
     *
     * @return The average percentage difference of the values in the history.
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
     * @param v The value to add to the first list.
     */
    public void addFirst(double v)
    {
        firsts.add(v);
    }

    /**
     * Adds a new value to the second list of values.
     *
     * @param v The value to add to the second list.
     */
    public void addSecond(double v)
    {
        seconds.add(v);
    }

    /**
     * Adds new values to both the first and second lists.
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
     * Clears the history of both the first and second lists.
     */
    public void clear()
    {
        firsts.clear();
        seconds.clear();
    }

    /**
     * Gets the minimum size of the two history lists.
     * The size is based on the smaller of the two lists.
     *
     * @return The size of the smallest history list.
     */
    public int size()
    {
        return Math.min(firsts.size(), seconds.size());
    }
}
