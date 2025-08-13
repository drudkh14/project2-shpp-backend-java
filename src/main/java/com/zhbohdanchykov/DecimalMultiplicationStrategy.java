package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DecimalMultiplicationStrategy implements MultiplicationStrategy {

    private final BigDecimal minValue;
    private final BigDecimal maxValue;

    private final Logger logger = LoggerFactory.getLogger(DecimalMultiplicationStrategy.class);

    public DecimalMultiplicationStrategy(Class<? extends Number> type) throws IllegalArgumentException {
        try {
            this.minValue = getMinValue(type);
            this.maxValue = getMaxValue(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        logger.info("Created DecimalMultiplicationStrategy for type {}.", type);
    }

    private BigDecimal getMaxValue(Class<? extends Number> type) throws IllegalArgumentException {
        if (type.equals(Float.class)) {
            return BigDecimal.valueOf(Float.MAX_VALUE);
        } else if (type.equals(Double.class)) {
            return BigDecimal.valueOf(Double.MAX_VALUE);
        } else {
            throw new IllegalArgumentException("Unsupported type for DecimalMultiplicationStrategy: " + type);
        }
    }

    private BigDecimal getMinValue(Class<? extends Number> type) throws IllegalArgumentException {
        if (type.equals(Float.class)) {
            return BigDecimal.valueOf(Float.MIN_NORMAL);
        } else if (type.equals(Double.class)) {
            return BigDecimal.valueOf(Double.MIN_NORMAL);
        } else {
            throw new IllegalArgumentException("Unsupported type for DecimalMultiplicationStrategy: " + type);
        }
    }

    @Override
    public Number multiply(Number a, Number b) {
        logger.trace("Got {} and {} for multiplying in DecimalMultiplicationStrategy.", a, b);
        BigDecimal aValue = BigDecimal.valueOf(a.doubleValue());
        BigDecimal bValue = BigDecimal.valueOf(b.doubleValue());
        BigDecimal result = aValue.multiply(bValue);
        if (isNotInRange(result)) {
            throw new IllegalArgumentException("Out of range: " + result + "for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public Number increment(Number a, Number step) {
        logger.trace("Got {} and {} for incrementing in DecimalMultiplicationStrategy.", a, step);
        BigDecimal aValue = BigDecimal.valueOf(a.doubleValue());
        BigDecimal stepValue = BigDecimal.valueOf(step.doubleValue());
        BigDecimal result = aValue.add(stepValue);
        if (isNotInRange(result)) {
            throw new IllegalArgumentException("Out of range: " + result + " for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public boolean lessThan(Number a, Number b) {
        logger.trace("Got {} and {} for lessThan() in DecimalMultiplicationStrategy.", a, b);
        return BigDecimal.valueOf(a.doubleValue()).compareTo(BigDecimal.valueOf(b.doubleValue())) < 0;
    }

    @Override
    public boolean areEqual(Number a, Number b) {
        logger.trace("Got {} and {} for areEqual() in DecimalMultiplicationStrategy.", a, b);
        return BigDecimal.valueOf(a.doubleValue()).compareTo(BigDecimal.valueOf(b.doubleValue())) == 0;
    }

    @Override
    public boolean lessThanOrEqual(Number a, Number b) {
        logger.trace("Got {} and {} for lessThanOrEqual() in DecimalMultiplicationStrategy.", a, b);
        return lessThan(a, b) || areEqual(a, b);
    }

    @Override
    public boolean greaterThan(Number a, Number b) {
        logger.trace("Got {} and {} for greaterThan() in DecimalMultiplicationStrategy.", a, b);
        return BigDecimal.valueOf(a.doubleValue()).compareTo(BigDecimal.valueOf(b.doubleValue())) > 0;
    }

    @Override
    public boolean greaterThanOrEqual(Number a, Number b) {
        logger.trace("Got {} and {} for greaterThanOrEqual() in DecimalMultiplicationStrategy.", a, b);
        return greaterThan(a, b) || areEqual(a, b);
    }


    private boolean isNotInRange(BigDecimal result) {
        logger.trace("Got {} for range control in floating number for {} and {}.", result, minValue, maxValue);
        return (result.abs().compareTo(maxValue) > 0 || result.abs().compareTo(minValue) < 0)
                && result.compareTo(BigDecimal.valueOf(0)) != 0;
    }
}
