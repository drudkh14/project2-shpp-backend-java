package com.zhbohdanchykov;

public interface MultiplicationStrategy {
    Number multiply(Number a, Number b);
    Number increment(Number a, Number step);
    boolean lessThan(Number a, Number b);
    boolean areEqual(Number a, Number b);
    boolean lessThanOrEqual(Number a, Number b);
    boolean greaterThan(Number a, Number b);
    boolean greaterThanOrEqual(Number a, Number b);
}
