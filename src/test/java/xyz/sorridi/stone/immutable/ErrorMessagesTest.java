package xyz.sorridi.stone.immutable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorMessagesTest
{
    private static final String INIT_PART   = "The value is %s.";
    private static final String FINAL_PART  = " expect: ";

    @Test
    void expect()
    {
        String string = ErrorMessages.EMPTY.expect("something");
        String result = String.format(INIT_PART, "empty") + FINAL_PART + "something";

        assertEquals(result, string);
    }

    @ParameterizedTest
    @ValueSource(classes = { Location.class, World.class, Player.class })
    void expect(Object arg)
    {
        String string = ErrorMessages.NULL.expect(arg);
        String result = String.format(INIT_PART, "null") + FINAL_PART;

        switch (arg.getClass().getSimpleName())
        {
            case "Location" -> assertEquals(result + "org.bukkit.Location", string);
            case "World"    -> assertEquals(result + "org.bukkit.World", string);
            case "Player"   -> assertEquals(result + "org.bukkit.entity.Player", string);
        }
    }

}