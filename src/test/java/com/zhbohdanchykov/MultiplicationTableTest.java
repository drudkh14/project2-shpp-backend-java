package com.zhbohdanchykov;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MultiplicationTableTest {

    @ParameterizedTest
    @CsvSource({
            "1, 3, 1",
            "3, 1, -1",
            "2, 10, 2",
            "120, 4, -5"
    })
    void testMultiplicationTable(int min, int max, int inc) {
        MultiplicationConfig config = new MultiplicationConfig(min, max, inc);

        NumberStrategy strategy = mock(NumberStrategy.class);

        when(strategy.multiply(any(), any())).thenAnswer(invocation -> {
            int a = invocation.getArgument(0);
            int b = invocation.getArgument(1);
            return a * b;
        });

        when(strategy.increment(any(), any())).thenAnswer(invocation -> {
            int a = invocation.getArgument(0);
            int b = invocation.getArgument(1);
            return a + b;
        });

        when(strategy.lessThanOrEqual(any(), any())).thenAnswer(invocation -> {
            int a = invocation.getArgument(0);
            int b = invocation.getArgument(1);
            return a <= b;
        });

        when(strategy.greaterThanOrEqual(any(), any())).thenAnswer(invocation -> {
            int a = invocation.getArgument(0);
            int b = invocation.getArgument(1);
            return a >= b;
        });

        when(strategy.lessThan(any(), any())).thenAnswer(invocation -> {
            int a = invocation.getArgument(0);
            int b = invocation.getArgument(1);
            return a < b;
        });

        MultiplicationTable table = new MultiplicationTable(config, strategy);
        table.printMultiplicationTable();

        int iterationsOuter = Math.abs(max - min) / Math.abs(inc) + 1;
        int iterationsNumber = iterationsOuter * (iterationsOuter + 1) / 2;

        verify(strategy,times(iterationsNumber)).multiply(any(), any());
    }
}