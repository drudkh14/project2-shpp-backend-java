package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class IntegerStrategy implements NumberStrategy {

    private final BigInteger minValue;
    private final BigInteger maxValue;

    private final Logger logger = LoggerFactory.getLogger(IntegerStrategy.class);

    public IntegerStrategy(Number minValue, Number maxValue) {
        this.minValue = BigInteger.valueOf(minValue.longValue());
        this.maxValue = BigInteger.valueOf(maxValue.longValue());
    }

    @Override
    public Number multiply(Number a, Number b) {
        logger.trace("Got {} and {} for multiplying in IntegerMultiplicationStrategy.", a, b);
        BigInteger firstOperand = BigInteger.valueOf(a.longValue());
        BigInteger secondOperand = BigInteger.valueOf(b.longValue());
        BigInteger result = firstOperand.multiply(secondOperand);
        if (result.compareTo(minValue) < 0 || result.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Out of range: " + result + " for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public Number increment(Number a, Number step) {
        logger.trace("Got {} and {} for incrementing in IntegerMultiplicationStrategy.", a, step);
        BigInteger firstOperand = BigInteger.valueOf(a.longValue());
        BigInteger secondOperand = BigInteger.valueOf(step.longValue());
        BigInteger result = firstOperand.add(secondOperand);
        if (result.compareTo(minValue) < 0 || result.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Out of range: " + result + " for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public boolean lessThan(Number a, Number b) {
        logger.trace("Got {} and {} for lessThan() in IntegerMultiplicationStrategy.", a, b);
        return BigInteger.valueOf(a.longValue()).compareTo(BigInteger.valueOf(b.longValue())) < 0;
    }

    @Override
    public boolean areEqual(Number a, Number b) {
        logger.trace("Got {} and {} for areEqual() in IntegerMultiplicationStrategy.", a, b);
        return BigInteger.valueOf(a.longValue()).compareTo(BigInteger.valueOf(b.longValue())) == 0;
    }

    @Override
    public boolean lessThanOrEqual(Number a, Number b) {
        logger.trace("Got {} and {} for lessThanOrEqual() in IntegerMultiplicationStrategy.", a, b);
        return lessThan(a, b) || areEqual(a, b);
    }

    @Override
    public boolean greaterThan(Number a, Number b) {
        logger.trace("Got {} and {} for greaterThan() in IntegerMultiplicationStrategy.", a, b);
        return BigInteger.valueOf(a.longValue()).compareTo(BigInteger.valueOf(b.longValue())) > 0;
    }

    @Override
    public boolean greaterThanOrEqual(Number a, Number b) {
        logger.trace("Got {} and {} for greaterThanOrEqual() in IntegerMultiplicationStrategy.", a, b);
        return greaterThan(a, b) || areEqual(a, b);
    }
}
