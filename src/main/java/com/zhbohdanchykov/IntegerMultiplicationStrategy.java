package com.zhbohdanchykov;

import java.math.BigInteger;

public class IntegerMultiplicationStrategy implements MultiplicationStrategy {

    private final BigInteger minValue;
    private final BigInteger maxValue;

    public IntegerMultiplicationStrategy(Class<? extends Number> type) {
        this.minValue = getMinValue(type);
        this.maxValue = getMaxValue(type);
    }

    private BigInteger getMaxValue(Class<? extends Number> type) {
        if (type.equals(Integer.class)) {
            return BigInteger.valueOf(Integer.MAX_VALUE);
        } else if (type.equals(Long.class)) {
            return BigInteger.valueOf(Long.MAX_VALUE);
        } else if (type.equals(Byte.class)) {
            return BigInteger.valueOf(Byte.MAX_VALUE);
        } else if (type.equals(Short.class)) {
            return BigInteger.valueOf(Short.MAX_VALUE);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    private BigInteger getMinValue(Class<? extends Number> type) {
        if (type.equals(Integer.class)) {
            return BigInteger.valueOf(Integer.MIN_VALUE);
        } else if (type.equals(Long.class)) {
            return BigInteger.valueOf(Long.MIN_VALUE);
        } else if (type.equals(Byte.class)) {
            return BigInteger.valueOf(Byte.MIN_VALUE);
        } else if (type.equals(Short.class)) {
            return BigInteger.valueOf(Short.MIN_VALUE);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    @Override
    public Number multiply(Number a, Number b) {
        BigInteger firstOperand = BigInteger.valueOf(a.longValue());
        BigInteger secondOperand = BigInteger.valueOf(b.longValue());
        BigInteger result = firstOperand.multiply(secondOperand);
        if (result.compareTo(minValue) < 0 || result.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Out of range: " + result + "for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public Number increment(Number a, Number step) {
        BigInteger firstOperand = BigInteger.valueOf(a.longValue());
        BigInteger secondOperand = BigInteger.valueOf(step.longValue());
        BigInteger result = firstOperand.add(secondOperand);
        if (result.compareTo(minValue) < 0 || result.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Out of range: " + result + "for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public boolean lessThan(Number a, Number b) {
        return BigInteger.valueOf(a.longValue()).compareTo(BigInteger.valueOf(b.longValue())) < 0;
    }

    @Override
    public boolean areEqual(Number a, Number b) {
        return BigInteger.valueOf(a.longValue()).compareTo(BigInteger.valueOf(b.longValue())) == 0;
    }

    @Override
    public boolean lessThanOrEqual(Number a, Number b) {
        return lessThan(a, b) || areEqual(a, b);
    }
}
