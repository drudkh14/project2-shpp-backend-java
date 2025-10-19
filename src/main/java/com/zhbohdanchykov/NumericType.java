package com.zhbohdanchykov;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

public enum NumericType {

    BYTE(Byte.MIN_VALUE, Byte.MAX_VALUE, Byte::parseByte),
    SHORT(Short.MIN_VALUE, Short.MAX_VALUE, Short::parseShort),
    INTEGER(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer::parseInt),
    LONG(Long.MIN_VALUE, Long.MAX_VALUE, Long::parseLong),
    FLOAT(Float.MIN_NORMAL, Float.MAX_VALUE, Float::parseFloat),
    DOUBLE(Double.MIN_NORMAL, Double.MAX_VALUE, Double::parseDouble),;


    private final Number minValue;
    private final Number maxValue;
    private final Function<String, Number> func;

    NumericType(Number minValue, Number maxValue,
                Function<String, Number> func) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.func = func;
    }

    public static NumericType fromString(String name) throws IllegalArgumentException {
        NumericType type;
        try {
            type = NumericType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unexpected type for multiplication table: %s. You should use numeric types.".formatted(name));
        }
        return type;
    }

    public Number castValue(String value) throws NumberFormatException {
        return func.apply(value);
    }

    public NumberStrategy getNumberStrategy() {
        NumberStrategy strategy;
        if (this.name().equals("FLOAT") || this.name().equals("DOUBLE")) {
            strategy = new DecimalStrategy(BigDecimal.valueOf(minValue.doubleValue()),
                    BigDecimal.valueOf(maxValue.doubleValue())
            );
        } else {
            strategy = new IntegerStrategy(BigInteger.valueOf(minValue.longValue()),
                    BigInteger.valueOf(maxValue.longValue()));
        }
        return strategy;
    }
}
