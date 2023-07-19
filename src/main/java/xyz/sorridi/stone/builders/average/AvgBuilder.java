package xyz.sorridi.stone.builders.average;

import lombok.Getter;

/**
 * Class used to calculate the average of a given set of numbers. Lightweight version of {@link AvgHistory}.
 *
 * @author Sorridi
 * @since 1.0
 */
public class AvgBuilder
{
    private final int maxCount;

    private int count = 0;
    private double sum = 0;

    @Getter
    private double start, end;

    private AvgHistory avgHistory;

    public AvgBuilder()
    {
        this(Integer.MAX_VALUE);
    }

    public AvgBuilder(int maxCount)
    {
        this.maxCount = maxCount;
    }

    public AvgBuilder(int maxCount, boolean history)
    {
        this.maxCount = maxCount;
        avgHistory = history ? new AvgHistory(maxCount) : null;
    }

    /**
     * Sets the start time.
     */
    public void start()
    {
        start = System.nanoTime();
    }

    /**
     * Sets the end time and adds the average to the sum.
     */
    public void stop()
    {
        end = System.nanoTime() - start;
        add(end);
    }

    /**
     * Adds a new value to the average.
     *
     * @param value The value to add.
     */
    public void add(double value)
    {
        sum += count != maxCount ? value : -sum;
        count += count != maxCount ? 1 : -count;
    }

    /**
     * Gets the average in milliseconds.
     *
     * @return Average in milliseconds.
     */
    public double get()
    {
        return get(1_000_000);
    }

    /**
     * Gets the average in the specified unit.
     *
     * @param div The unit to divide by.
     * @return Average in the specified unit.
     */
    public double get(double div)
    {
        return sum / div / count;
    }

    /**
     * Saves a sample of the average in milliseconds.
     */
    public void sample()
    {
        sample(1_000_000);
    }

    /**
     * Saves a sample of the average in the specified unit.
     *
     * @param value The value to add.
     */
    public void sample(long value)
    {
        if (avgHistory != null)
        {
            avgHistory.add(get(value));
        }
    }

    /**
     * Gets the average history.
     *
     * @return The average history.
     */
    public double getAverageFromHistory()
    {
        return avgHistory.getAverage();
    }

}