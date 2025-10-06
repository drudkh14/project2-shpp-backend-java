package com.zhbohdanchykov;

import java.math.BigDecimal;
import java.math.BigInteger;

public enum NumericType {

    BYTE(Byte.class, "byte", Byte.MIN_VALUE, Byte.MAX_VALUE),
    SHORT(Short.class, "short", Short.MIN_VALUE, Short.MAX_VALUE),
    INTEGER(Integer.class, "int", Integer.MIN_VALUE, Integer.MAX_VALUE),
    LONG(Long.class, "long", Long.MIN_VALUE, Long.MAX_VALUE),
    FLOAT(Float.class, "float", Float.MIN_NORMAL, Float.MAX_VALUE),
    DOUBLE(Double.class, "double", Double.MIN_NORMAL, Double.MAX_VALUE),;


    private final Class<? extends Number> clazz;
    private final String typeName;
    private final Number minValue;
    private final Number maxValue;

    NumericType(Class<? extends Number> clazz, String name, Number minValue, Number maxValue) {
        this.clazz = clazz;
        this.typeName = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static NumericType fromString(String name) throws IllegalArgumentException {
        for (NumericType type : NumericType.values()) {
            if (type.typeName.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
                ("Unexpected type for multiplication table: %s. You should use numeric types.").formatted(name));
    }

    public Number castValue(String value) throws NumberFormatException {
        Number num = null;

        try {
            if (clazz.equals(Byte.class)) {
                num = Byte.parseByte(value);
            } else if (clazz.equals(Short.class)) {
                num = Short.parseShort(value);
            } else if (clazz.equals(Integer.class)) {
                num = Integer.parseInt(value);
            } else if (clazz.equals(Long.class)) {
                num = Long.parseLong(value);
            } else if (clazz.equals(Float.class)) {
                num = Float.parseFloat(value);
            } else if (clazz.equals(Double.class)) {
                num = Double.parseDouble(value);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Was unable to cast {" + value + "} to " + clazz + ".");
        }

        return num;
    }

    public NumberStrategy getNumberStrategy() {
        NumberStrategy strategy;
        if (clazz.equals(Float.class) || clazz.equals(Double.class)) {
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
