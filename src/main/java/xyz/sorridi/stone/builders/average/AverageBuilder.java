package xyz.sorridi.stone.builders.average;

import lombok.Getter;

/**
 * Monitors averages of execution times. Lightweight.
 * @author Sorridi
 * @since 1.0
 */
public class AverageBuilder
{
    private final int maxCount;

    private int count   = 0;
    private double sum  = 0;

    @Getter
    private double start, end;

    private AverageHistory averageHistory;

    public AverageBuilder(int maxCount)
    {
        this.maxCount = maxCount;
    }

    public AverageBuilder(int maxCount, boolean history)
    {
        this.maxCount   = maxCount;
        averageHistory  = history ? new AverageHistory(maxCount) : null;
    }

    /**
     * Sets the start time.
     */
    public void setStart()
    {
        start = System.nanoTime();
    }

    /**
     * Sets the end time and adds the average to the sum.
     */
    public void setEnd()
    {
        end = System.nanoTime() - start;
        add(end);
    }

    /**
     * Adds a new value to the average.
     * @param value The value to add.
     */
    public void add(double value)
    {
        sum     += count != maxCount ? value : -sum;
        count   += count != maxCount ? 1 : -count;
    }

    /**
     * Gets the average in milliseconds.
     * @return Average in milliseconds.
     */
    public double get()
    {
        return get(1_000_000);
    }

    /**
     * Gets the average in the specified unit.
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
     * @param value The value to add.
     */
    public void sample(long value)
    {
        if (averageHistory != null)
        {
            averageHistory.add(get(value));
        }
    }

    /**
     * Gets the average history.
     * @return The average history.
     */
    public double getAverageFromHistory()
    {
        return averageHistory.getAverage();
    }

}