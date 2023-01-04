package xyz.sorridi.stone.builders.average;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageHistoryTest
{

    @Test
    void add()
    {
        AverageHistory averageHistory = new AverageHistory(2);
        averageHistory.add(10);
        averageHistory.add(20);
        averageHistory.add(30);

        assertEquals(25, averageHistory.getAverage());
    }

    @Test
    void getAverage()
    {
        AverageHistory averageHistory = new AverageHistory(10);
        averageHistory.add(10);
        averageHistory.add(20);
        averageHistory.add(30);

        assertEquals(20, averageHistory.getAverage());
    }

    @Test
    void clear()
    {
        AverageHistory averageHistory = new AverageHistory(10);
        averageHistory.add(10_000);
        averageHistory.clear();

        assertEquals(0, averageHistory.getAverage());
    }

}