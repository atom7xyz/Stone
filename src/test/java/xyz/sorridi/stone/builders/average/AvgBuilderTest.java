package xyz.sorridi.stone.builders.average;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvgBuilderTest
{

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100})
    void get(int average)
    {
        AvgBuilder avgBuilder = new AvgBuilder(10);
        avgBuilder.add(100);
        avgBuilder.add(200);
        avgBuilder.add(300);

        switch (average)
        {
            case 1 -> assertEquals(200, avgBuilder.get(average));
            case 2 -> assertEquals(100, avgBuilder.get(average));
            case 10 -> assertEquals(20, avgBuilder.get(average));
            case 100 -> assertEquals(2.0, avgBuilder.get(average));
        }
    }

    @Test
    void testGet()
    {
        AvgBuilder avgBuilder = new AvgBuilder(10);
        avgBuilder.add(10);
        avgBuilder.add(20);
        avgBuilder.add(30);

        assertEquals(0.00002, avgBuilder.get());
    }

    @Test
    @SneakyThrows
    void getAverageFromHistory()
    {
        AvgBuilder avgBuilder = new AvgBuilder(10, true);

        avgBuilder.start();
        Thread.sleep(50);
        avgBuilder.stop();

        avgBuilder.sample();
        assertTrue(avgBuilder.getAverageFromHistory() >= 50);
    }

}