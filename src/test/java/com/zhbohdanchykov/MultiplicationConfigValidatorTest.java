package com.zhbohdanchykov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Properties;
import java.util.stream.Stream;

class MultiplicationConfigValidatorTest {

    private static Stream<NumericType> numericTypesProvider() {
        return Stream.of(NumericType.BYTE, NumericType.SHORT, NumericType.INTEGER,
                NumericType.LONG, NumericType.FLOAT, NumericType.DOUBLE);
    }

    @Test
    void testValidatorSuccessful() {
        Properties props1 = new Properties();
        props1.setProperty("min", "1");
        props1.setProperty("max", "5");
        props1.setProperty("inc", "1");

        Properties props2 = new Properties();
        props2.setProperty("min", "5");
        props2.setProperty("max", "1");
        props2.setProperty("inc", "-1");

        MultiplicationConfigValidator validator1 = new MultiplicationConfigValidator(props1, NumericType.INTEGER);
        MultiplicationConfigValidator validator2 = new MultiplicationConfigValidator(props2, NumericType.INTEGER);
        Assertions.assertEquals(new MultiplicationConfig(1, 5, 1), validator1.validate());
        Assertions.assertEquals(new MultiplicationConfig(5, 1, -1), validator2.validate());
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testMissingMinProperty(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("max", "1");
        props.setProperty("inc", "1");

        Properties props1 = new Properties();
        props1.setProperty("min", "");
        props1.setProperty("max", "1");
        props1.setProperty("inc", "1");

        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);

        MultiplicationConfigValidator validator1 = new MultiplicationConfigValidator(props1, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator1::validate);
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testMissingMaxProperty(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("min", "1");
        props.setProperty("inc", "1");

        Properties props1 = new Properties();
        props1.setProperty("min", "1");
        props1.setProperty("max", "");
        props1.setProperty("inc", "1");

        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);

        MultiplicationConfigValidator validator1 = new MultiplicationConfigValidator(props1, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator1::validate);
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testMissingIncProperty(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("min", "1");
        props.setProperty("max", "5");

        Properties props1 = new Properties();
        props1.setProperty("min", "1");
        props1.setProperty("max", "5");
        props1.setProperty("inc", "");


        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);

        MultiplicationConfigValidator validator1 = new MultiplicationConfigValidator(props1, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator1::validate);
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testMinMaxEqual(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("min", "5");
        props.setProperty("max", "5");
        props.setProperty("inc", "1");

        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testZeroInc(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("min", "1");
        props.setProperty("max", "5");
        props.setProperty("inc", "0");

        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testNegIncMinLessThanMax(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("min", "1");
        props.setProperty("max", "5");
        props.setProperty("inc", "-1");

        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);
    }

    @ParameterizedTest
    @MethodSource("numericTypesProvider")
    void testPosMaxLessThanMin(NumericType numericType) {
        Properties props = new Properties();
        props.setProperty("min", "5");
        props.setProperty("max", "1");
        props.setProperty("inc", "1");

        MultiplicationConfigValidator validator = new MultiplicationConfigValidator(props, numericType);
        Assertions.assertThrows(IllegalArgumentException.class, validator::validate);
    }
}