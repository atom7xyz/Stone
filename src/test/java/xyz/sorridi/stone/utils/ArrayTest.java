package xyz.sorridi.stone.utils;

import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.utils.data.Array;

import java.util.List;

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

        List<Integer> e = List.of(1, 2, 3, 4, 5);
        Integer[] f = Array.of(e, Integer[].class);

        assertArrayEquals(c, f);
    }

}