//package com.zhbohdanchykov;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.math.BigInteger;
//import java.util.stream.Stream;
//
//class IntegerMultiplicationStrategyTest {
//
//    public static Stream<Class<? extends Number>> classProvider() {
//        return Stream.of(Byte.class, Short.class, Integer.class, Long.class);
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testMultiply(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        Assertions.assertEquals(BigInteger.valueOf(6), strategy.multiply(2, 3));
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testMultiplyZero(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        Assertions.assertEquals(BigInteger.valueOf(0), strategy.multiply(3, 0));
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testMultiplyException(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        if (clazz.equals(Integer.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Integer.MAX_VALUE / 2 + 1, 2));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Integer.MIN_VALUE / 2 - 1, 2));
//        } else if (clazz.equals(Long.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Long.MAX_VALUE / 2 + 1, 2));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Long.MIN_VALUE / 2 - 1, 2));
//        } else if (clazz.equals(Byte.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Byte.MAX_VALUE / 2 + 1, 2));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Byte.MIN_VALUE / 2 - 1, 2));
//        } else if (clazz.equals(Short.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Short.MAX_VALUE / 2 + 1, 2));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.multiply(Short.MIN_VALUE / 2 - 1, 2));
//        }
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testIncrement(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        Assertions.assertEquals(BigInteger.valueOf(4), strategy.increment(3, 1));
//        Assertions.assertEquals(BigInteger.valueOf(5), strategy.increment(3, 2));
//        Assertions.assertEquals(BigInteger.valueOf(2), strategy.increment(3, -1));
//        Assertions.assertEquals(BigInteger.valueOf(1), strategy.increment(3, -2));
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testIncrementException(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        if (clazz.equals(Integer.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Integer.MAX_VALUE, 1));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Integer.MIN_VALUE, -1));
//        } else if (clazz.equals(Long.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Long.MAX_VALUE, 1));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Long.MIN_VALUE, -1));
//        } else if (clazz.equals(Byte.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Byte.MAX_VALUE, 1));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Byte.MIN_VALUE, -1));
//        } else if (clazz.equals(Short.class)) {
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Short.MAX_VALUE, 1));
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    () -> strategy.increment(Short.MIN_VALUE, -1));
//        }
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testLessThen(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        Assertions.assertTrue(strategy.lessThan(2, 3));
//        Assertions.assertFalse(strategy.lessThan(3, 0));
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testAreEqual(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        Assertions.assertTrue(strategy.areEqual(2, 2));
//        Assertions.assertFalse(strategy.areEqual(1, 2));
//    }
//
//    @ParameterizedTest
//    @MethodSource("classProvider")
//    void testLessThenOrEqual(Class<? extends Number> clazz) {
//        IntegerStrategy strategy = new IntegerStrategy(clazz);
//        Assertions.assertTrue(strategy.lessThanOrEqual(2, 3));
//        Assertions.assertFalse(strategy.lessThanOrEqual(3, 0));
//        Assertions.assertTrue(strategy.lessThanOrEqual(3, 3));
//    }
//}