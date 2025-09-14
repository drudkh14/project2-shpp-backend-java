package com.zhbohdanchykov;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MultiplicationTable {

    private final MultiplicationStrategy strategy;
    private final Number min;
    private final Number max;
    private final Number inc;

    private final Logger logger = LoggerFactory.getLogger(MultiplicationTable.class);
    private final Logger printer = LoggerFactory.getLogger("PrinterLogger");

    public MultiplicationTable(Class<? extends Number> type, Properties props) throws IllegalArgumentException {
        try {
            this.strategy = getMultiplicationStrategy(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.max = castValue(props.getProperty("max"), type);
        this.min = castValue(props.getProperty("min"), type);
        this.inc = castValue(props.getProperty("inc"), type);

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

        logger.info("Created Multiplication Table and got min {} max {} increment {} strategy {}.",
                min, max, inc, strategy);
    }

    private Number castValue(String value, Class<? extends Number> type) {
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

    public void printMultiplicationTable() throws JsonProcessingException {
        logger.info("Starting printing of multiplication table.");
        for (Number i = min; shouldContinue(i, max, inc); i = strategy.increment(i, inc)) {
            for (Number j = i; shouldContinue(j, max, inc); j = strategy.increment(j, inc)) {
                Number result = strategy.multiply(i, j);
                logger.trace("Multiplication result {} for {} and {}.", result, i, j);
                printCurrentMultiplication(new MultiplicationContainer(i, j, result));
            }
        }
        logger.info("Finished printing of multiplication table.");
    }

    private void printCurrentMultiplication(MultiplicationContainer multiplicationContainer)
            throws JsonProcessingException {
        Serializer<MultiplicationContainer> serializer = new Serializer<>(multiplicationContainer);
        String output;
        String format = System.getProperty("format");
        if (format == null) {
            output = multiplicationContainer.toString();
            logger.info("No format specified for multiplication table. Using standard output.");
        } else {
            logger.info("Using format {}.", format);
            output = format.equals("xml") ? serializer.serializeToXml() : serializer.serializeToJson();
        }
        printer.info(output);
    }

    private boolean shouldContinue(Number a, Number b, Number inc) {
        if (strategy.lessThan(inc, 0)) {
            return strategy.greaterThanOrEqual(a, b);
        } else {
            return strategy.lessThanOrEqual(a, b);
        }
    }
}


