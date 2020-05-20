package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.Arithmetic;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArithmeticUnitTests {

    private double deltaValue = 0.0001;

    @Test
    public void addTwoNumbersWithDetailingMathMethod() {
        double num1 = 2.0408;
        double num2 = 3.0615;
        boolean isDetailingMath = true;

        double expected = 5.1107;

        double actual = Arithmetic.add(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void addTwoNumbersWithStandardMathMethod() {
        double num1 = 2.0408;
        double num2 = 3.0615;
        boolean isDetailingMath = false;

        double expected = 5.1023;

        double actual = Arithmetic.add(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void subtractTwoNumbersWithDetailingMathMethod() {
        double num1 = 4.0408;
        double num2 = 3.0615;
        boolean isDetailingMath = true;

        double expected = 0.0909;

        double actual = Arithmetic.subtract(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void subtractTwoNumbersWithStandardMathMethod() {
        double num1 = 2.0408;
        double num2 = 3.0615;
        boolean isDetailingMath = false;

        double expected = -1.0207;

        double actual = Arithmetic.subtract(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void multiplyTwoNumbersWithDetailingMathMethod() {
        double num1 = 4.0708;
        double num2 = 11.0204;
        boolean isDetailingMath = true;

        double expected = 51.0815;

        double actual = Arithmetic.multiply(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void multiplyTwoNumbersWithStandardMathMethod() {
        double num1 = 4.0708;
        double num2 = 11.0204;
        boolean isDetailingMath = false;

        double expected = 44.8618;

        double actual = Arithmetic.multiply(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void divideTwoNumbersWithDetailingMathMethod() {
        double num1 = 21.0204;
        double num2 = 5.0011;
        boolean isDetailingMath = true;

        double expected = 4.0204;

        double actual = Arithmetic.divide(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void divideTwoNumbersWithStandardMathMethod() {
        double num1 = 21.0204;
        double num2 = 5.0011;
        boolean isDetailingMath = false;

        double expected = 4.2032;

        double actual = Arithmetic.divide(num1, num2, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }
}
