package xyz.sorridi.stone.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArrayTest
{

    @Test
    void of()
    {
        String[] a = new String[] { "Hello", "World" };
        String[] b = Array.of("Hello", "World");

        assertArrayEquals(a, b);

        Integer[] c = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] d = Array.of(1, 2, 3, 4, 5);

        assertArrayEquals(c, d);
    }

}