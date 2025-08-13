package com.zhbohdanchykov;

import java.math.BigDecimal;

public class DecimalMultiplicationStrategy implements MultiplicationStrategy {

    private final BigDecimal minValue;
    private final BigDecimal maxValue;

    public DecimalMultiplicationStrategy(Class<? extends Number> type) {
        this.minValue = getMinValue(type);
        this.maxValue = getMaxValue(type);
    }

    private BigDecimal getMaxValue(Class<? extends Number> type) {
        if (type.equals(Float.class)) {
            return BigDecimal.valueOf(Float.MAX_VALUE);
        } else if (type.equals(Double.class)) {
            return BigDecimal.valueOf(Double.MAX_VALUE);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    private BigDecimal getMinValue(Class<? extends Number> type) {
        if (type.equals(Float.class)) {
            return BigDecimal.valueOf(Float.MIN_NORMAL);
        } else if (type.equals(Double.class)) {
            return BigDecimal.valueOf(Double.MIN_NORMAL);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    @Override
    public Number multiply(Number a, Number b) {
        BigDecimal aValue = BigDecimal.valueOf(a.doubleValue());
        BigDecimal bValue = BigDecimal.valueOf(b.doubleValue());
        BigDecimal result = aValue.multiply(bValue);
        if ((result.compareTo(maxValue) > 0 || result.compareTo(minValue) < 0) &&
                result.compareTo(BigDecimal.valueOf(0)) != 0) {
            throw new IllegalArgumentException("Out of range: " + result + "for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public Number increment(Number a, Number step) {
        BigDecimal aValue = BigDecimal.valueOf(a.doubleValue());
        BigDecimal stepValue = BigDecimal.valueOf(step.doubleValue());
        BigDecimal result = aValue.add(stepValue);
        if (result.compareTo(maxValue) > 0 || result.compareTo(minValue) < 0) {
            throw new IllegalArgumentException("Out of range: " + result + "for: " + minValue + " and " + maxValue);
        }
        return result;
    }

    @Override
    public boolean lessThan(Number a, Number b) {
        return BigDecimal.valueOf(a.doubleValue()).compareTo(BigDecimal.valueOf(b.doubleValue())) < 0;
    }

    @Override
    public boolean areEqual(Number a, Number b) {
        return BigDecimal.valueOf(a.doubleValue()).compareTo(BigDecimal.valueOf(b.doubleValue())) == 0;
    }

    @Override
    public boolean lessThanOrEqual(Number a, Number b) {
        return lessThan(a, b) || areEqual(a, b);
    }
}
