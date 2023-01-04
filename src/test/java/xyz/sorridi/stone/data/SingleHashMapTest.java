package xyz.sorridi.stone.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleHashMapTest
{

    @Test
    void put()
    {
        SingleHashMap<String> map = new SingleHashMap<>();

        map.put("test");
        map.put("test");

        assertTrue(map.containsKey("test"));

        assertNull(map.put("new"));
        assertEquals("new", map.put("new"));

        assertEquals(2, map.size());
    }

}