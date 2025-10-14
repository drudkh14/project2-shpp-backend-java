package com.zhbohdanchykov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

class DecimalStrategyTest {

    private static Stream<Arguments> valueProvider() {
        return Stream.of(Arguments.of(BigDecimal.valueOf(Float.MIN_NORMAL), BigDecimal.valueOf(Float.MAX_VALUE)),
                Arguments.of(BigDecimal.valueOf(Double.MIN_NORMAL), BigDecimal.valueOf(Double.MAX_VALUE)));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiply(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Number result = strategy.multiply(2.5, 4.0);
        int compare = BigDecimal.valueOf(10.0).compareTo(BigDecimal.valueOf(result.doubleValue()));
        Assertions.assertEquals(0, compare);
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyZero(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Number result = strategy.multiply(5.5, 0.0);
        int compare = BigDecimal.valueOf(0.0).compareTo(BigDecimal.valueOf(result.doubleValue()));
        Assertions.assertEquals(0, compare);
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyOverflowException(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.multiply(max, 2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyUnderflowException(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        BigDecimal negMax = max.negate();
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.multiply(negMax, 2));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testMultiplyUnnormalizedException(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.multiply(min, 0.5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.multiply(min, -0.5));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrement(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertEquals(BigDecimal.valueOf(3.5), strategy.increment(2.5, 1.0));
        Assertions.assertEquals(BigDecimal.valueOf(4.5), strategy.increment(2.5, 2.0));
        Assertions.assertEquals(BigDecimal.valueOf(4.6), strategy.increment(2.5, 2.1));
        Assertions.assertEquals(BigDecimal.valueOf(5.5), strategy.increment(6.6, -1.1));
        Assertions.assertEquals(BigDecimal.valueOf(6.61), strategy.increment(6.62, -0.01));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrementOverflowException(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.increment(max, 1.0));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrementUnderflowException(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        BigDecimal negMax = max.negate();
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.increment(negMax, -1.0));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testIncrementUnnormalizedException(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        BigDecimal increment = (min.divide(BigDecimal.valueOf(2.0))).negate();
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.increment(min, increment));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testLessThan(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertTrue(strategy.lessThan(0.001, 0.01));
        Assertions.assertFalse(strategy.lessThan(0.01, -0.5));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testAreEqual(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertTrue(strategy.areEqual(2.5, 2.5));
        Assertions.assertFalse(strategy.areEqual(0.001, 0.0011));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testLessThanOrEqual(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertTrue(strategy.lessThanOrEqual(2.5, 2.5));
        Assertions.assertTrue(strategy.lessThanOrEqual(-3.4, -3.4));
        Assertions.assertTrue(strategy.lessThanOrEqual(0.001, 0.0011));
        Assertions.assertFalse(strategy.lessThanOrEqual(0.0011, 0.001));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testGreaterThan(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertTrue(strategy.greaterThan(0.01, 0.001));
        Assertions.assertFalse(strategy.greaterThan(-0.5, 0.01));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testGreaterThanOrEqual(BigDecimal min, BigDecimal max) {
        DecimalStrategy strategy = new DecimalStrategy(min, max);
        Assertions.assertTrue(strategy.greaterThanOrEqual(1.5, 1.5));
        Assertions.assertTrue(strategy.greaterThanOrEqual(-3.4, -3.4));
        Assertions.assertTrue(strategy.greaterThanOrEqual(0.01, 0.001));
        Assertions.assertFalse(strategy.greaterThanOrEqual(0.01, 0.2));
        Assertions.assertFalse(strategy.greaterThanOrEqual(-5.0, 0.001));
        Assertions.assertFalse(strategy.greaterThanOrEqual(0.001, 0.0011));
    }
}