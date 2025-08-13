package com.zhbohdanchykov;

import java.util.ArrayList;
import java.util.Properties;

public class MultiplicationTable {

    private final MultiplicationStrategy strategy;
    private final Number min;
    private final Number max;
    private final Number inc;

    public MultiplicationTable(Class<? extends Number> type, Properties props) {
        this.strategy = getMultiplicationStrategy(type);
        this.max = cast(props.getProperty("max"), type);
        this.min = cast(props.getProperty("min"), type);
        if (props.getProperty("inc").equals("0")) {
            throw new IllegalArgumentException("Increment must be a positive number");
        }

        if (props.getProperty("inc").contains("-") && strategy.lessThanOrEqual(min, max)) {
                throw new IllegalArgumentException("For negative increment maximal value must to be less than min");
            }


        this.inc = cast(props.getProperty("inc"), type);
    }

    private Number cast(String value, Class<? extends Number> type) {
        Number result = null;
        if (type.equals(Integer.class)) {
            result = Integer.valueOf(value);
        } else if (type.equals(Double.class)) {
            result = Double.valueOf(value);
        } else if (type.equals(Long.class)) {
            result = Long.valueOf(value);
        } else if (type.equals(Float.class)) {
            result = Float.valueOf(value);
        } else if (type.equals(Short.class)) {
            result = Short.valueOf(value);
        } else if (type.equals(Byte.class)) {
            result = Byte.valueOf(value);
        }
        return result;
    }

    private MultiplicationStrategy getMultiplicationStrategy(Class<? extends Number> type) {
        if (type.equals(Double.class) || type.equals(Float.class)) {
            return new DecimalMultiplicationStrategy(type);
        } else {
            return new IntegerMultiplicationStrategy(type);
        }
    }

    public ArrayList<MultiplicationContainer> getMultiplicationTable() {
        ArrayList<MultiplicationContainer> multiplications = new ArrayList<>();
        for (Number i = min; strategy.lessThanOrEqual(i, max); i = strategy.increment(i, inc)) {
            for (Number j = i; strategy.lessThanOrEqual(j, max); j = strategy.increment(j, inc)) {
                Number result = strategy.multiply(i, j);
                MultiplicationContainer current = new MultiplicationContainer(i, j, result);
                multiplications.add(current);
            }
        }
        return multiplications;
    }
}


