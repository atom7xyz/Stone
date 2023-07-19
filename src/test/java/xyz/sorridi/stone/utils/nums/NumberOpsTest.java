package xyz.sorridi.stone.utils.nums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOpsTest
{
    @Test
    void safeDiv()
    {
        assertEquals(0, NumberOps.safeDiv(0, 0));
        assertEquals(0, NumberOps.safeDiv(0, 1));
        assertEquals(-.5, NumberOps.safeDiv(-1, 2));

        assertEquals(5, NumberOps.safeDiv(10, 2));
        assertEquals(10, NumberOps.safeDiv(100, 10));
    }

    @Test
    void shorten()
    {
        assertEquals("100.0", NumberOps.shorten(100));
        assertEquals("1.0k", NumberOps.shorten(1_000));
        assertEquals("1.0M", NumberOps.shorten(1_000_000));
        assertEquals("1.0B", NumberOps.shorten(1_000_000_000));

        assertEquals("2.4k", NumberOps.shorten(2_449));
        assertEquals("2.5k", NumberOps.shorten(2_450));
    }
}