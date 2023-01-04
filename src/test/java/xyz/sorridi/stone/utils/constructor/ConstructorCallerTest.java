package xyz.sorridi.stone.utils.constructor;

import lombok.val;
import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.PotionEffectSerializer;
import xyz.sorridi.stone.data.SingleHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConstructorCallerTest
{

    @Test
    void call()
    {
        val map     = ConstructorCaller.call(SingleHashMap.class);
        val serial  = ConstructorCaller.call(PotionEffectSerializer.class);

        assertTrue(map.isPresent());
        assertTrue(serial.isPresent());
    }

}