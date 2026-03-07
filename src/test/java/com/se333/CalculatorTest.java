package com.se333;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    // Tests for add()
    @Test
    public void testAddPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    public void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    public void testAddMixedNumbers() {
        assertEquals(1, calculator.add(5, -4));
        assertEquals(-1, calculator.add(-5, 4));
    }

    @Test
    public void testAddZero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(0, calculator.add(0, 0));
    }

    // Tests for subtract()
    @Test
    public void testSubtractPositiveNumbers() {
        assertEquals(1, calculator.subtract(5, 4));
    }

    @Test
    public void testSubtractNegativeNumbers() {
        assertEquals(-1, calculator.subtract(-5, -4));
    }

    @Test
    public void testSubtractMixedNumbers() {
        assertEquals(9, calculator.subtract(5, -4));
    }

    @Test
    public void testSubtractZero() {
        assertEquals(5, calculator.subtract(5, 0));
    }

    // Tests for multiply()
    @Test
    public void testMultiplyPositiveNumbers() {
        assertEquals(12, calculator.multiply(3, 4));
    }

    @Test
    public void testMultiplyNegativeNumbers() {
        assertEquals(12, calculator.multiply(-3, -4));
    }

    @Test
    public void testMultiplyMixedNumbers() {
        assertEquals(-12, calculator.multiply(-3, 4));
    }

    @Test
    public void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 0));
    }

    // Tests for divide()
    @Test
    public void testDividePositiveNumbers() {
        assertEquals(2.0, calculator.divide(8, 4));
    }

    @Test
    public void testDivideWithRemainder() {
        assertEquals(2.5, calculator.divide(5, 2));
    }

    @Test
    public void testDivideNegativeNumbers() {
        assertEquals(-2.0, calculator.divide(-8, 4));
    }

    @Test
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
    }

    // Tests for isEven()
    @Test
    public void testIsEvenWithEvenPositive() {
        assertTrue(calculator.isEven(4));
        assertTrue(calculator.isEven(2));
    }

    @Test
    public void testIsEvenWithOddPositive() {
        assertFalse(calculator.isEven(3));
        assertFalse(calculator.isEven(1));
    }

    @Test
    public void testIsEvenWithNegativeEven() {
        assertTrue(calculator.isEven(-4));
    }

    @Test
    public void testIsEvenWithNegativeOdd() {
        assertFalse(calculator.isEven(-3));
    }

    @Test
    public void testIsEvenWithZero() {
        assertTrue(calculator.isEven(0));
    }

    // Tests for factorial()
    @Test
    public void testFactorialZero() {
        assertEquals(1, calculator.factorial(0));
    }

    @Test
    public void testFactorialOne() {
        assertEquals(1, calculator.factorial(1));
    }

    @Test
    public void testFactorialPositive() {
        assertEquals(2, calculator.factorial(2));
        assertEquals(6, calculator.factorial(3));
        assertEquals(24, calculator.factorial(4));
        assertEquals(120, calculator.factorial(5));
    }

    @Test
    public void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-10));
    }
}