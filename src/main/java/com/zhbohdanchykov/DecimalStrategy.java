package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DecimalStrategy implements NumberStrategy {
    private final BigDecimal maxValue;
    private final BigDecimal minValue;
    private final BigDecimal minNormal;

    private final Logger logger = LoggerFactory.getLogger(DecimalStrategy.class);

    public DecimalStrategy(BigDecimal minNormal, BigDecimal maxValue) {
        this.minNormal = minNormal;
        this.maxValue = maxValue;
        this.minValue = BigDecimal.valueOf(-maxValue.doubleValue());
    }

    @Override
    public Number multiply(Number a, Number b) {
        logger.trace("Got {} and {} for multiplying in DecimalMultiplicationStrategy.", a, b);
        BigDecimal aValue = BigDecimal.valueOf(a.doubleValue());
        BigDecimal bValue = BigDecimal.valueOf(b.doubleValue());
        BigDecimal result = aValue.multiply(bValue);

        if (isNotInRange(result)) {
            throw new IllegalArgumentException("Out of range: " + result + " for: " + minValue + " and " + maxValue);
        }

        if (isNotNormal(result)) {
            throw new IllegalArgumentException("Unnormalized floating value: " + result);
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

        if (isNotNormal(result)) {
            throw new IllegalArgumentException("Unnormalized floating value: " + result);
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
        return (result.abs().compareTo(maxValue) > 0);
    }

    private boolean isNotNormal(BigDecimal result) {
        logger.trace("Got {} for normality control in floating number for {}.", result, minNormal);
        return (result.abs().compareTo(minNormal) < 0 && result.compareTo(BigDecimal.ZERO) != 0);
    }
}
