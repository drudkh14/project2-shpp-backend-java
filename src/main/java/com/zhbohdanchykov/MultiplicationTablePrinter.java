package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MultiplicationTablePrinter {

    private static final Logger logger = LoggerFactory.getLogger(MultiplicationTablePrinter.class);

    private final ArrayList<MultiplicationContainer> multiplications;

    public MultiplicationTablePrinter(MultiplicationTableCreator multiplicationTableCreator) {
        this.multiplications = multiplicationTableCreator.getMultiplicationTable();
    }

    public void print() {
        int longestString = 0;
        Number row = multiplications.getFirst().firstOperand();
        for (MultiplicationContainer multiplication : multiplications) {
            if (multiplication.toString().length() > longestString) {
                longestString = multiplication.toString().length();
            }

        }
        int columnWidth = longestString + 2;
        int leftPadding = 1;

        for (MultiplicationContainer multiplication : multiplications) {
            if (!row.equals(multiplication.firstOperand())) {
                logger.info("|\n");
                row = multiplication.firstOperand();
            }

            int totalPadding = columnWidth - multiplication.toString().length();
            int rightPadding = totalPadding - leftPadding;

            String formatted = String.format("|%" + leftPadding + "s%s%" + rightPadding + "s",
                    "", multiplication, "");
            logger.info(formatted);
        }
        logger.info("|\n");
    }
}
