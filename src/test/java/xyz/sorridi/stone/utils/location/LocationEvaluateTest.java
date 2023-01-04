package xyz.sorridi.stone.utils.location;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationEvaluateTest {

    @Test
    void isEqual()
    {
        Location location   = new Location(null, 1, 2, 3);
        Location location2  = new Location(null, 1, 2, 3);
        Location location3  = new Location(null, 1, 2, 4);

        assertTrue(LocationEvaluate.isEqual(location, location2));
        assertFalse(LocationEvaluate.isEqual(location, location3));
    }

    @Test
    void getMiddleLocation()
    {
        Location location   = new Location(null, 10, 10, 10, 100, 100);
        Location middle     = LocationEvaluate.getMiddleLocation(location, true);

        assertEquals(location.add(.5, 0, .5), middle);
    }

}