package xyz.sorridi.stone.data;

import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.data.structures.SoftMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SoftMapTest
{

    @Test
    void put()
    {
        SoftMap<Integer, String> softMap = new SoftMap<>();

        for (int i = 0; i < 100; i++)
        {
            softMap.put(i, i + " b");
        }

        assertEquals("0 b", softMap.get(0));
        assertEquals(100, softMap.size());
    }

}