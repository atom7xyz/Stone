package xyz.sorridi.stone.utils.constructor;

import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.PotionEffectSerializer;
import xyz.sorridi.stone.data.structures.SingleMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConstructorCallerTest
{

    @Test
    void call()
    {
        var map = ConstructorCaller.call(SingleMap.class);
        var serial = ConstructorCaller.call(PotionEffectSerializer.class);

        assertTrue(map.isPresent());
        assertTrue(serial.isPresent());
    }

}