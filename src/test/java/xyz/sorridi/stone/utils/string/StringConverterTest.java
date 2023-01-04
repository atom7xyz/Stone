package xyz.sorridi.stone.utils.string;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringConverterTest
{

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 30, 120, 160 })
    void fromMinutesToHHmm(int minutes)
    {
        String formatted = StringConverter.fromMinutesToHHmm(minutes);

        switch (minutes)
        {
            case 1      -> assertEquals("00h 01m", formatted);
            case 2      -> assertEquals("00h 02m", formatted);
            case 30     -> assertEquals("00h 30m", formatted);
            case 120    -> assertEquals("02h 00m", formatted);
            case 160    -> assertEquals("02h 40m", formatted);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "§a§l-1", "§a§l2", "00§a§l30", "§a00§l120", "7§a00160" })
    void extractNumber(String string)
    {
        long extracted = StringConverter.extractNumber(string);

        switch ((int) extracted)
        {
            case 1      -> assertEquals(1, extracted);
            case 2      -> assertEquals(2, extracted);
            case 30     -> assertEquals(30, extracted);
            case 120    -> assertEquals(120, extracted);
            case 160    -> assertEquals(700160, extracted);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "ciao_ciao", "ciao_c_i_a_o", "ciao", "CC_ii_aa_OO" })
    void toCamelCase(String string)
    {
        String converted = StringConverter.toCamelCase(string);

        switch (string)
        {
            case "ciao_ciao"    -> assertEquals("CiaoCiao", converted);
            case "ciao_c_i_a_o" -> assertEquals("CiaoCIAO", converted);
            case "ciao"         -> assertEquals("Ciao", converted);
            case "CC_ii_aa_OO"  -> assertEquals("CcIiAaOo", converted);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "ciao_ciao", "ciao_c_i_a_o", "ciao", "CC_ii_aa_OO" })
    void toProperCase(String string)
    {
        String converted = StringConverter.toProperCase(string);

        switch (string)
        {
            case "ciao_ciao"    -> assertEquals("Ciao_ciao", converted);
            case "ciao_c_i_a_o" -> assertEquals("Ciao_c_i_a_o", converted);
            case "ciao"         -> assertEquals("Ciao", converted);
            case "CC_ii_aa_OO"  -> assertEquals("Cc_ii_aa_oo", converted);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 100_000_000, 1_000_000_000 })
    void convertTime(long time)
    {
        String converted = StringConverter.convertTime(time);

        switch ((int) time)
        {
            case 0              -> assertEquals("01:00:00 01/01/70", converted);
            case 100_000_000    -> assertEquals("04:46:40 02/01/70", converted);
            case 1_000_000_000  -> assertEquals("14:46:40 12/01/70", converted);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 100_000_000, 1_000_000_000 })
    void testConvertTime(long time)
    {
        String converted = StringConverter.convertTime("dd/MM/yyyy", time);

        switch ((int) time)
        {
            case 0              -> assertEquals("01/01/1970", converted);
            case 100_000_000    -> assertEquals("02/01/1970", converted);
            case 1_000_000_000  -> assertEquals("12/01/1970", converted);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 7, 105, 1_600 })
    void toRoman(int number)
    {
        String converted = StringConverter.toRoman(number);

        switch (number)
        {
            case 7      -> assertEquals("VII", converted);
            case 105    -> assertEquals("CV", converted);
            case 1_600  -> assertEquals("MDC", converted);
        }
    }

}