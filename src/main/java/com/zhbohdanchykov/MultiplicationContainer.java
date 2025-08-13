package com.zhbohdanchykov;

public record MultiplicationContainer(Number firstOperand, Number secondOperand, Number result) {
    public String toString() {
        return firstOperand + " * " + secondOperand + " = " + result;
    }
}
