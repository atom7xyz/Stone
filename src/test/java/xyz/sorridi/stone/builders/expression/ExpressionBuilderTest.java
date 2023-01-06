package xyz.sorridi.stone.builders.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionBuilderTest
{

    @Test
    void getResult()
    {
        ExpressionBuilder builder = new ExpressionBuilder("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )");
        assertEquals(101, builder.getResult());

        builder = new ExpressionBuilder("( -5 * ( 10 - 1 ) )");
        assertEquals(45, builder.getResult());
    }

}