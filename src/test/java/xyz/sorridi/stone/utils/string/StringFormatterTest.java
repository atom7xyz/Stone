package xyz.sorridi.stone.utils.string;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringFormatterTest {

    @ParameterizedTest
    @ValueSource(doubles = { 1.5556, 2.0001, 30.060400115 })
    void format(double value)
    {
        String six = StringFormatter.format(value, 6);

        if (value == 1.5556)
        {
            assertEquals("1.555600", six);
        }
        else if (value == 2.0001)
        {
            assertEquals("2.000100", six);
        }
        else if (value == 30.060400115)
        {
            assertEquals("30.060400", six);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.5556, 2.0001, 30.060400115 })
    void formatSingle(double value)
    {
        String formatted = StringFormatter.formatSingle(value);

        if (value == 1.5556)
        {
            assertEquals("1.6", formatted);
        }
        else if (value == 2.0001)
        {
            assertEquals("2.0", formatted);
        }
        else if (value == 30.060400115)
        {
            assertEquals("30.1", formatted);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.5556, 2.0001, 30.060400115 })
    void formatDouble(double value)
    {
        String formatted = StringFormatter.formatDouble(value);

        
        if (value == 1.5556)
        {
            assertEquals("1.56", formatted);
        }
        else if (value == 2.0001)
        {
            assertEquals("2.00", formatted);
        }
        else if (value == 30.060400115)
        {
            assertEquals("30.06", formatted);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.5556, 2.0001, 30.060400115 })
    void formatTriple(double value)
    {
        String formatted = StringFormatter.formatTriple(value);

        if (value == 1.5556)
        {
            assertEquals("1.556", formatted);
        }
        else if (value == 2.0001)
        {
            assertEquals("2.000", formatted);
        }
        else if (value == 30.060400115)
        {
            assertEquals("30.060", formatted);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.5556, 2.0001, 30.060400115 })
    void formatQuadruple(double value)
    {
        String formatted = StringFormatter.formatQuadruple(value);

        if (value == 1.5556)
        {
            assertEquals("1.5556", formatted);
        }
        else if (value == 2.0001)
        {
            assertEquals("2.0001", formatted);
        }
        else if (value == 30.060400115)
        {
            assertEquals("30.0604", formatted);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.5556, 2.0001, 30.060400115 })
    void formatQuintuple(double value)
    {
        String formatted = StringFormatter.formatQuintuple(value);

        if (value == 1.5556)
        {
            assertEquals("1.55560", formatted);
        }
        else if (value == 2.0001)
        {
            assertEquals("2.00010", formatted);
        }
        else if (value == 30.060400115)
        {
            assertEquals("30.06040", formatted);
        }
    }

}