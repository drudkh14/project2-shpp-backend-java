package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Properties;

public class MultiplicationTableCreator {

    private final MultiplicationStrategy strategy;
    private final Number min;
    private final Number max;
    private final Number inc;

    private final Logger logger = LoggerFactory.getLogger(MultiplicationTableCreator.class);

    public MultiplicationTableCreator(Class<? extends Number> type, Properties props) throws IllegalArgumentException {
        try {
            this.strategy = getMultiplicationStrategy(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.max = cast(props.getProperty("max"), type);
        this.min = cast(props.getProperty("min"), type);
        this.inc = cast(props.getProperty("inc"), type);

        if (strategy.areEqual(min, max)) {
            logger.debug("Min {} and max {} are the same.", min, max);
            throw new IllegalArgumentException("Min and max are the same.");
        }

        if (strategy.areEqual(inc, 0)) {
            logger.debug("Got zero increment value.");
            throw new IllegalArgumentException("Increment must be a positive number.");
        }

        if (strategy.lessThan(inc, 0) && strategy.lessThan(min, max)) {
            logger.debug("Got negative increment {} and minimal {} is less than or equal to maximal {}.",
                    inc, min, max);
            throw new IllegalArgumentException("For negative increment maximal value must to be less than minimal.");
        }
    }

    private Number cast(String value, Class<? extends Number> type) {
        logger.debug("Casting value {} to type {}.", value, type);
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
        logger.debug("Returning casted value {}.", result);
        return result;
    }

    private MultiplicationStrategy getMultiplicationStrategy(Class<? extends Number> type) {
        logger.debug("Getting multiplication strategy for type {}.", type);
        if (type.equals(Double.class) || type.equals(Float.class)) {
            logger.debug("Returning DecimalMultiplicationStrategy for type {}.", type);
            try {
                return new DecimalMultiplicationStrategy(type);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            logger.debug("Returning IntegerMultiplicationStrategy for type {}.", type);
            try {
                return new IntegerMultiplicationStrategy(type);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public ArrayList<MultiplicationContainer> getMultiplicationTable() {
        ArrayList<MultiplicationContainer> multiplications = new ArrayList<>();
        logger.debug("Created ArrayList for multiplication table.");
        for (Number i = min; shouldContinue(i, max, inc); i = strategy.increment(i, inc)) {
            for (Number j = i; shouldContinue(j, max, inc); j = strategy.increment(j, inc)) {
                Number result = strategy.multiply(i, j);
                logger.trace("Multiplication result {} for {} and {}.", result, i, j);
                MultiplicationContainer current = new MultiplicationContainer(i, j, result);
                multiplications.add(current);
            }
        }
        logger.debug("Multiplication table created.");
        return multiplications;
    }

    private boolean shouldContinue(Number a, Number b, Number inc) {
        if (strategy.lessThan(inc, 0)) {
            return strategy.greaterThanOrEqual(a, b);
        } else {
            return strategy.lessThanOrEqual(a, b);
        }
    }
}


