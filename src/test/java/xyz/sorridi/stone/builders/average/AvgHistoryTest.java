package xyz.sorridi.stone.builders.average;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgHistoryTest
{

    @Test
    void add()
    {
        AvgHistory avgHistory = new AvgHistory(2);
        avgHistory.add(10);
        avgHistory.add(20);
        avgHistory.add(30);

        assertEquals(25, avgHistory.getAverage());
    }

    @Test
    void getAverage()
    {
        AvgHistory avgHistory = new AvgHistory(10);
        avgHistory.add(10);
        avgHistory.add(20);
        avgHistory.add(30);

        assertEquals(20, avgHistory.getAverage());
    }

    @Test
    void clear()
    {
        AvgHistory avgHistory = new AvgHistory(10);
        avgHistory.add(10_000);
        avgHistory.clear();

        assertEquals(0, avgHistory.getAverage());
    }

}