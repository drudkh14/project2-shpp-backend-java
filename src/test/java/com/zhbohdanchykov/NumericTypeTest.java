package com.zhbohdanchykov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class NumericTypeTest {

    public static Stream<Arguments> providerGetNumberStrategy() {
        return Stream.of(Arguments.of(NumericType.BYTE, IntegerStrategy.class),
                Arguments.of(NumericType.SHORT, IntegerStrategy.class),
                Arguments.of(NumericType.INTEGER, IntegerStrategy.class),
                Arguments.of(NumericType.LONG, IntegerStrategy.class),
                Arguments.of(NumericType.FLOAT, DecimalStrategy.class),
                Arguments.of(NumericType.DOUBLE, DecimalStrategy.class)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "byte, BYTE",
            "short, SHORT",
            "integer, INTEGER",
            "long, LONG",
            "float, FLOAT",
            "double, DOUBLE"
    })
    void testFromString(String type, NumericType expected) {
        Assertions.assertEquals(expected, NumericType.fromString(type));
    }

    @Test
    void testFromStringException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> NumericType.fromString("string"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> NumericType.fromString("boolean"));
    }

    @ParameterizedTest
    @CsvSource({
            "BYTE, 100, 100",
            "SHORT, 1000, 1000",
            "INTEGER, 9999, 9999",
            "LONG, 123456789, 123456789",
            "FLOAT, 1.2306e36, 1.2306E36",
            "FLOAT, 1.5678E37, 1.5678E37",
            "DOUBLE, 3.14E100, 3.14E100",
            "DOUBLE, 2.5005e201, 2.5005E201"
    })
    void testCastValue(NumericType type, String numberToCast, String expected) {
        Assertions.assertEquals(expected, type.castValue(numberToCast).toString());
        Assertions.assertEquals("-" + expected, type.castValue("-" + numberToCast).toString());
    }

    @ParameterizedTest
    @CsvSource({
            "BYTE, abc",
            "SHORT, 3e5",
            "INTEGER, 25.7",
            "LONG, 0.2864",
            "FLOAT, 35f6",
            "DOUBLE, 34gh%"
    })
    void testCastValueException(NumericType type, String numberToCast) {
        Assertions.assertThrows(NumberFormatException.class, () -> type.castValue(numberToCast));
    }

    @ParameterizedTest
    @MethodSource("providerGetNumberStrategy")
    void testGetNumberStrategy(NumericType type, Class<? extends NumberStrategy> expected) {
        Assertions.assertEquals(expected, type.getNumberStrategy().getClass());
    }
}