package xyz.sorridi.stone.utils.bukkit.location;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationEvaluateTest
{

    @Test
    void isEqual()
    {
        Location location = new Location(null, 1, 2, 3);
        Location location2 = new Location(null, 1, 2, 3);
        Location location3 = new Location(null, 1, 2, 4);

        assertTrue(LocationEvaluate.isEqual(location, location2));
        assertFalse(LocationEvaluate.isEqual(location, location3));
    }

    @Test
    void getMiddleLocation()
    {
        Location location = new Location(null, 10, 10, 10, 100, 100);
        Location middle = LocationEvaluate.getMiddleLocation(location, true);

        assertEquals(location.add(.5, 0, .5), middle);
    }

    @Test
    void isSimilar()
    {
        Location location = new Location(null, 1.4, 2.7, 3.0001);
        Location location2 = new Location(null, 1.5, 2.3, 3.1);
        Location location3 = new Location(null, 1, 2, 4);

        assertTrue(LocationEvaluate.isSimilar(location, location2));
        assertFalse(LocationEvaluate.isSimilar(location, location3));
    }

    @Test
    void getMiddleLocationFull()
    {
        Location location = new Location(null, 10, 10, 10);
        Location middle = LocationEvaluate.getMiddleLocationFull(location, false);

        assertEquals(location.add(.5, .5, .5), middle);
    }

}