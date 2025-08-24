package com.zhbohdanchykov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

class DecimalMultiplicationStrategyTest {

    public static Stream<Class<? extends Number>> classProvider() {
        return Stream.of(Float.class, Double.class);
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testMultiply(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        Number result = strategy.multiply(2.5, 4.0);
        int compare = BigDecimal.valueOf(10.0).compareTo(BigDecimal.valueOf(result.doubleValue()));
        Assertions.assertEquals(0, compare);
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testMultiplyZero(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        Number result = strategy.multiply(5.5, 0.0);
        int compare = BigDecimal.valueOf(0.0).compareTo(BigDecimal.valueOf(result.doubleValue()));
        Assertions.assertEquals(0, compare);
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testMultiplyException(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        if (clazz.equals(Float.class)) {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.multiply(Float.MAX_VALUE, 2.0));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.multiply(Float.MIN_NORMAL, 0.5));
        } else {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.multiply(Double.MAX_VALUE, 2.0));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.multiply(Double.MIN_NORMAL, 0.5));
        }
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testIncrement(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        Assertions.assertEquals(BigDecimal.valueOf(3.5), strategy.increment(2.5, 1.0));
        Assertions.assertEquals(BigDecimal.valueOf(4.5), strategy.increment(2.5, 2.0));
        Assertions.assertEquals(BigDecimal.valueOf(4.6), strategy.increment(2.5, 2.1));
        Assertions.assertEquals(BigDecimal.valueOf(5.5), strategy.increment(6.6, -1.1));
        Assertions.assertEquals(BigDecimal.valueOf(6.61), strategy.increment(6.62, -0.01));
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testIncrementException(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        if (clazz.equals(Float.class)) {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.increment(Float.MAX_VALUE, 1.0));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.increment(Float.MIN_NORMAL, -(Float.MIN_NORMAL / 2.0)));
        } else {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.increment(Double.MAX_VALUE, 1.0));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> strategy.increment(Double.MIN_NORMAL, -(Double.MIN_NORMAL / 2.0f)));
        }
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testLessThan(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        Assertions.assertTrue(strategy.lessThan(0.001, 0.01));
        Assertions.assertFalse(strategy.lessThan(0.01, 0.001));
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testAreEqual(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        Assertions.assertTrue(strategy.areEqual(2.5, 2.5));
        Assertions.assertFalse(strategy.areEqual(0.001, 0.0011));
    }

    @ParameterizedTest
    @MethodSource("classProvider")
    void testLessThanOrEqual(Class<? extends Number> clazz) {
        DecimalMultiplicationStrategy strategy = new DecimalMultiplicationStrategy(clazz);
        Assertions.assertTrue(strategy.lessThanOrEqual(2.5, 2.5));
        Assertions.assertTrue(strategy.lessThanOrEqual(0.001, 0.0011));
        Assertions.assertFalse(strategy.lessThanOrEqual(0.0011, 0.001));
    }
}