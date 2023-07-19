package xyz.sorridi.stone.data;

import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.data.structures.SingleMap;

import static org.junit.jupiter.api.Assertions.*;

class SingleMapTest
{

    @Test
    void put()
    {
        SingleMap<String> map = new SingleMap<>();

        map.put("test");
        map.put("test");

        assertTrue(map.containsKey("test"));

        assertNull(map.put("new"));
        assertEquals("new", map.put("new"));

        assertEquals(2, map.size());
    }

}