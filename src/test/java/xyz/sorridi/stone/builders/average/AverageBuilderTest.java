package xyz.sorridi.stone.builders.average;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AverageBuilderTest
{

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 10, 100 })
    void get(int average)
    {
        AverageBuilder averageBuilder = new AverageBuilder(10);
        averageBuilder.add(100);
        averageBuilder.add(200);
        averageBuilder.add(300);

        switch (average)
        {
            case 1      -> assertEquals(200, averageBuilder.get(average));
            case 2      -> assertEquals(100, averageBuilder.get(average));
            case 10     -> assertEquals(20, averageBuilder.get(average));
            case 100    -> assertEquals(2.0, averageBuilder.get(average));
        }
    }

    @Test
    void testGet()
    {
        AverageBuilder averageBuilder = new AverageBuilder(10);
        averageBuilder.add(10);
        averageBuilder.add(20);
        averageBuilder.add(30);

        assertEquals(0.00002, averageBuilder.get());
    }

    @Test
    @SneakyThrows
    void getAverageFromHistory()
    {
        AverageBuilder averageBuilder = new AverageBuilder(10, true);

        averageBuilder.setStart();
        Thread.sleep(100);
        averageBuilder.setEnd();

        averageBuilder.sample();
        assertTrue(averageBuilder.getAverageFromHistory() >= 100);
    }

}