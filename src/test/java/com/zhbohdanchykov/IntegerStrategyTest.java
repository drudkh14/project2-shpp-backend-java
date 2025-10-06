package com.zhbohdanchykov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

class IntegerStrategyTest {

    public static Stream<Arguments> valueProvider() {
        return Stream.of(Arguments.of(BigInteger.valueOf(Byte.MIN_VALUE), BigInteger.valueOf(Byte.MAX_VALUE)),
                Arguments.of(BigInteger.valueOf(Short.MIN_VALUE), BigInteger.valueOf(Short.MAX_VALUE)),
                Arguments.of(BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(Integer.MAX_VALUE)),
                Arguments.of(BigInteger.valueOf(Long.MIN_VALUE), BigInteger.valueOf(Long.MAX_VALUE)));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiply(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertEquals(BigInteger.valueOf(6), strategy.multiply(2, 3));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyZero(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertEquals(BigInteger.valueOf(0), strategy.multiply(3, 0));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyOverflowException(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        long factor = max.longValue() / 2 + 1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.multiply(factor, 2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyUnderflowException(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        long factor = min.longValue() / 2 - 1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.multiply(factor, 2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrement(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertEquals(BigInteger.valueOf(4), strategy.increment(3, 1));
        Assertions.assertEquals(BigInteger.valueOf(5), strategy.increment(3, 2));
        Assertions.assertEquals(BigInteger.valueOf(2), strategy.increment(3, -1));
        Assertions.assertEquals(BigInteger.valueOf(1), strategy.increment(3, -2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrementOverflowException(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.increment(max, 1));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrementUnderflowException(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.increment(min, -1));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testLessThan(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertTrue(strategy.lessThan(2, 3));
        Assertions.assertFalse(strategy.lessThan(3, 0));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testAreEqual(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertTrue(strategy.areEqual(2, 2));
        Assertions.assertFalse(strategy.areEqual(1, 2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void lessThanOrEqual(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertTrue(strategy.lessThanOrEqual(2, 3));
        Assertions.assertFalse(strategy.lessThanOrEqual(3, 0));
        Assertions.assertTrue(strategy.lessThanOrEqual(3, 3));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void greaterThan(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertTrue(strategy.greaterThan(3, 2));
        Assertions.assertTrue(strategy.greaterThan(-1, -5));
        Assertions.assertFalse(strategy.greaterThan(-4, 1));
        Assertions.assertFalse(strategy.greaterThan(2, 2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void greaterThanOrEqual(BigInteger min, BigInteger max) {
        IntegerStrategy strategy = new IntegerStrategy(min, max);
        Assertions.assertTrue(strategy.greaterThanOrEqual(3, 2));
        Assertions.assertTrue(strategy.greaterThanOrEqual(-1, -5));
        Assertions.assertFalse(strategy.greaterThanOrEqual(-4, 1));
        Assertions.assertTrue(strategy.greaterThanOrEqual(2, 2));
        Assertions.assertTrue(strategy.greaterThanOrEqual(-5, -5));
    }
}