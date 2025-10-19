package com.zhbohdanchykov;

import java.util.Properties;

public class MultiplicationConfigValidator {

    private final NumberStrategy strategy;

    private final NumericType numericType;

    private final String min;
    private final String max;
    private final String inc;

    public MultiplicationConfigValidator(Properties properties, NumericType numericType) {
        this.strategy = numericType.getNumberStrategy();
        this.numericType = numericType;
        this.min = properties.getProperty("min");
        this.max = properties.getProperty("max");
        this.inc = properties.getProperty("inc");
    }

    public MultiplicationConfig validate() throws IllegalArgumentException {
        checkPropertiesAvailability();

        Number minNumber = numericType.castValue(min);
        Number maxNumber = numericType.castValue(max);
        Number incNumber = numericType.castValue(inc);

        checkMinMaxNotEqual(minNumber, maxNumber);
        checkIncrementNonZero(incNumber);
        checkMinMaxNegativeIncrement(minNumber, maxNumber, incNumber);
        checkMinMaxPositiveIncrement(minNumber, maxNumber, incNumber);

        return new MultiplicationConfig(minNumber, maxNumber, incNumber);
    }

    private void checkPropertiesAvailability() throws IllegalArgumentException {
        if (min == null || min.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: min.");
        }

        if (max == null || max.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: max.");
        }

        if (inc == null || inc.isEmpty()) {
            throw new IllegalArgumentException("Missing required property: inc.");
        }
    }

    private void checkMinMaxNotEqual(Number minNumber, Number maxNumber) throws IllegalArgumentException {
        if (strategy.areEqual(minNumber, maxNumber)) {
            throw new IllegalArgumentException("Min {" + minNumber + "} and max {" + maxNumber + "} are equal.");
        }
    }

    private void checkIncrementNonZero(Number incNumber) throws IllegalArgumentException {
        if (strategy.areEqual(incNumber, 0)) {
            throw new IllegalArgumentException("Increment must be a non-zero number.");
        }
    }

    private void checkMinMaxNegativeIncrement(Number minNumber, Number maxNumber, Number incNumber)
            throws IllegalArgumentException {
        if (strategy.lessThan(incNumber, 0) && strategy.lessThan(minNumber, maxNumber)) {
            throw new IllegalArgumentException(
                    "For negative increment {" + incNumber + "} min {" + minNumber +
                            "} must be greater than max {" + maxNumber + "}."
            );
        }
    }

    private void checkMinMaxPositiveIncrement(Number minNumber, Number maxNumber, Number incNumber)
            throws IllegalArgumentException {
        if (strategy.greaterThan(incNumber, 0) && strategy.greaterThan(minNumber, maxNumber)) {
            throw new IllegalArgumentException(
                    "For positive increment {" + incNumber + "} min {" + minNumber +
                            "} must be less than max {" + maxNumber + "}."
            );
        }
    }
}
