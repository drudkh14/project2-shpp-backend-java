package com.zhbohdanchykov;

import java.util.Properties;

public class MultiplicationConfigValidator {

    private final NumberStrategy strategy;

    private final String min;
    private final String max;
    private final String inc;

    private final Number minNumber;
    private final Number maxNumber;
    private final Number incNumber;

    public MultiplicationConfigValidator(Properties properties, NumericType numType, NumberStrategy strategy) {
        this.strategy = strategy;

        this.min = properties.getProperty("min");
        this.max = properties.getProperty("max");
        this.inc = properties.getProperty("inc");

        this.minNumber = numType.castValue(min);
        this.maxNumber = numType.castValue(max);
        this.incNumber = numType.castValue(inc);
    }

    public MultiplicationConfig validate() throws IllegalArgumentException {
        checkPropertiesAvailability();
        checkMinMaxNotEqual();
        checkIncrementNonZero();
        checkMinMaxNegativeIncrement();
        checkMinMaxPositiveIncrement();

        return new MultiplicationConfig(minNumber, maxNumber, incNumber);
    }

    private void checkPropertiesAvailability() throws IllegalArgumentException {
        if (min.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: min.");
        }

        if (max.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: max.");
        }

        if (inc.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: inc.");
        }
    }

    private void checkMinMaxNotEqual() throws IllegalArgumentException {
        if (strategy.areEqual(minNumber, maxNumber)) {
            throw new IllegalArgumentException("Min {" + minNumber + "} and max {" + maxNumber + "} are equal.");
        }
    }

    private void checkIncrementNonZero() throws IllegalArgumentException {
        if (strategy.areEqual(incNumber, 0)) {
            throw new IllegalArgumentException("Increment must be a non-zero number.");
        }
    }

    private void checkMinMaxNegativeIncrement() throws IllegalArgumentException {
        if (strategy.lessThan(incNumber, 0) && strategy.lessThan(minNumber, maxNumber)) {
            throw new IllegalArgumentException(
                    "For negative increment {" + incNumber + "} min {" + minNumber +
                            "} must be greater than max {" + maxNumber + "}."
            );
        }
    }

    private void checkMinMaxPositiveIncrement() throws IllegalArgumentException {
        if (strategy.greaterThan(incNumber, 0) && strategy.greaterThan(minNumber, maxNumber)) {
            throw new IllegalArgumentException(
                    "For positive increment {" + incNumber + "} min {" + minNumber +
                            "} must be less than max {" + maxNumber + "}."
            );
        }
    }
}
