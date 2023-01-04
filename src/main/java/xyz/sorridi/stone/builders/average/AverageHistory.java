package xyz.sorridi.stone.builders.average;

import java.util.LinkedList;
import java.util.Queue;

public class AverageHistory
{
    private final Queue<Double> entries;
    private final int maxEntries;

    public AverageHistory(int maxEntries)
    {
        this.maxEntries = maxEntries;
        entries         = new LinkedList<>();
    }

    /**
     * Adds a new value to the average.
     * @param value The value to add.
     */
    public void add(double value)
    {
        if (entries.size() == maxEntries)
        {
            entries.remove();
        }

        entries.add(value);
    }

    /**
     * Gets the average of the values in the history.
     * @return The average of the values in the history.
     */
    public long getAverage()
    {
        long sum = 0;

        for (double entry : entries)
        {
            sum += entry;
        }

        return sum == 0 ? 0 : sum / entries.size();
    }

    /**
     * Clears the history.
     */
    public void clear()
    {
        entries.clear();
    }

}
