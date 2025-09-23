package com.zhbohdanchykov;

import java.util.Properties;

public class MultiplicationConfigValidator {
    private final Properties properties;
    private final NumericType numericType;
    private final NumberStrategy numberStrategy;

    public MultiplicationConfigValidator(Properties properties, NumericType numericType, NumberStrategy numberStrategy) {
        this.properties = properties;
        this.numericType = numericType;
        this.numberStrategy = numberStrategy;
    }

    public MultiplicationConfig validate() {
        String min = properties.getProperty("min");
        String max = properties.getProperty("max");
        String inc = properties.getProperty("inc");

        if (min.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: min.");
        }

        if (max.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: max.");
        }

        if (inc.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: inc.");
        }


        Number minNumber = numericType.castValue(min);
        Number maxNumber = numericType.castValue(max);
        Number incNumber = numericType.castValue(inc);

        if (numberStrategy.areEqual(minNumber, maxNumber)) {
            throw new IllegalArgumentException("Min " + minNumber + " and max " + maxNumber + " are the same.");
        }

        if (numberStrategy.areEqual(incNumber, 0)) {
            throw new IllegalArgumentException("Increment must be a non-zero number.");
        }

        if (numberStrategy.lessThan(incNumber, 0) && numberStrategy.lessThan(minNumber, maxNumber)) {
                throw new IllegalArgumentException(
                        "For negative increment " + incNumber + " min " + minNumber + " must be greater than max " + maxNumber + "."
                );
        }

        if (numberStrategy.greaterThan(incNumber, 0) && numberStrategy.greaterThan(minNumber, maxNumber)) {
            throw new IllegalArgumentException(
                    "For positive increment " + incNumber + " min " + minNumber + " must be less than max " + maxNumber + "."
            );
        }

        return new MultiplicationConfig(minNumber, maxNumber, incNumber);
    }
}
