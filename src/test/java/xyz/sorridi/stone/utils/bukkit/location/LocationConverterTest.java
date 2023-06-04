package xyz.sorridi.stone.utils.bukkit.location;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationConverterTest
{

    @Test
    void concat()
    {
        String concatenated = LocationConverter.concat(10, 5, 10);
        assertEquals("10:5:10", concatenated);
    }

    @Test
    void extractString()
    {
        Location location = new Location(null, 10, 5, 10);

        String extracted    = LocationConverter.extractString(location, false, false);
        String precise      = LocationConverter.extractString(location, true, false);

        assertEquals("10:5:10", extracted);
        assertEquals("10.0:5.0:10.0", precise);

        extracted   = LocationConverter.extractString(location, false, true);
        precise     = LocationConverter.extractString(location, true, true);

        assertEquals("10:5:10:0:0", extracted);
        assertEquals("10.0:5.0:10.0:0.0:0.0", precise);
    }

}