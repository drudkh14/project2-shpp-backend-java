package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiplicationTable {

    private final NumberStrategy strategy;
    private final Number min;
    private final Number max;
    private final Number inc;

    private final Logger logger = LoggerFactory.getLogger(MultiplicationTable.class);
    private final Logger printer = LoggerFactory.getLogger("PrinterLogger");

    public MultiplicationTable(MultiplicationConfig config, NumberStrategy strategy) {
        this.min = config.min();
        this.max = config.max();
        this.inc = config.inc();
        this.strategy = strategy;
    }

    public void printMultiplicationTable() {
        logger.info("Starting printing of multiplication table.");
        for (Number i = min; shouldContinue(i, max, inc); i = strategy.increment(i, inc)) {
            for (Number j = i; shouldContinue(j, max, inc); j = strategy.increment(j, inc)) {
                Number result = strategy.multiply(i, j);
                logger.trace("Multiplication result {} for {} and {}.", result, i, j);
                printer.info("{} * {} = {}", i, j, result);
            }
        }
        logger.info("Finished printing of multiplication table.");
    }

    private boolean shouldContinue(Number a, Number b, Number inc) {
        if (strategy.lessThan(inc, 0)) {
            return strategy.greaterThanOrEqual(a, b);
        } else {
            return strategy.lessThanOrEqual(a, b);
        }
    }
}


