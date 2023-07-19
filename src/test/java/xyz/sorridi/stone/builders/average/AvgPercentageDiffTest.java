package xyz.sorridi.stone.builders.average;

import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.data.structures.SoftMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgPercentageDiffTest
{

    @Test
    void calculate()
    {
        SoftMap<String, Integer> map = new SoftMap<>();
        AvgPercentageDiff avgPercentageDiff = new AvgPercentageDiff();
        avgPercentageDiff.add(10, 100);
        avgPercentageDiff.add(10, 100);
        avgPercentageDiff.add(10, 100);
        avgPercentageDiff.add(10, 100);

        assertEquals(900, avgPercentageDiff.calculate());
    }

    @Test
    void addFirst()
    {
        AvgPercentageDiff avgPercentageDiff = new AvgPercentageDiff();
        avgPercentageDiff.addFirst(10);
        avgPercentageDiff.addFirst(10);
        avgPercentageDiff.addFirst(10);
        avgPercentageDiff.addFirst(10);

        assertEquals(0, avgPercentageDiff.size());
    }

    @Test
    void addSecond()
    {
        AvgPercentageDiff avgPercentageDiff = new AvgPercentageDiff();
        avgPercentageDiff.addSecond(10);
        avgPercentageDiff.addSecond(10);
        avgPercentageDiff.addSecond(10);
        avgPercentageDiff.addSecond(10);

        assertEquals(0, avgPercentageDiff.size());
    }

    @Test
    void add()
    {
        AvgPercentageDiff avgPercentageDiff = new AvgPercentageDiff();
        avgPercentageDiff.add(10, 20);
        avgPercentageDiff.add(10, 30);
        avgPercentageDiff.add(10, 40);
        avgPercentageDiff.add(10, 50);

        assertEquals(4, avgPercentageDiff.size());
    }

    @Test
    void clear()
    {
        AvgPercentageDiff avgPercentageDiff = new AvgPercentageDiff();
        avgPercentageDiff.add(10, 20);
        avgPercentageDiff.clear();

        assertEquals(0, avgPercentageDiff.size());
    }

}