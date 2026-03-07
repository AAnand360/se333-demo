package com.se333;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            return -1; // BUG: should throw ArithmeticException
        }
        return (double) a / b;
    }

    public boolean isEven(int n) {
        return n % 2 == 0;
    }

    public int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative input");
        }
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }
}