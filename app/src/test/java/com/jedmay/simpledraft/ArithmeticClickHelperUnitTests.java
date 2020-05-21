package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.ArithmeticClickHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArithmeticClickHelperUnitTests {

    private double deltaValue = 0.0001;

    @Test
    public void minusClickButtonBothNumbersOnOutputWindow() {
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(2.0408);
        listOfDouble.add(2.0400);
        boolean isDetailingMath = true;

        String outputNumber = null;

        double expected = 0.0008;

        double actual = ArithmeticClickHelper.minusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void minusClickButtonOneNumberOnOutputWindowOneNumberOnOutputNumber() {
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(2.0408);
        listOfDouble.add(2.0400);
        boolean isDetailingMath = true;

        String outputNumber = "0.04";

        double expected = 2.0000;

        double actual = ArithmeticClickHelper.minusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void minusClickButtonBlankListBlankOutputNumber() {
        List<Double> listOfDouble = new ArrayList<>();
        boolean isDetailingMath = true;

        String outputNumber = null;

        double expected = 0;

        double actual = ArithmeticClickHelper.minusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void minusClickButtonBlankListOneOutputNumber() {
        List<Double> listOfDouble = new ArrayList<>();
        boolean isDetailingMath = true;

        String outputNumber = "11.02";

        double expected = 0;

        double actual = ArithmeticClickHelper.minusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void plusClickButtonBothNumbersOnOutputWindow() {
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(2.0408);
        listOfDouble.add(2.0400);
        boolean isDetailingMath = true;

        String outputNumber = null;

        double expected = 4.0808;

        double actual = ArithmeticClickHelper.plusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void plusClickButtonOneNumberOnOutputWindowOneNumberOnOutputNumber() {
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(2.0408);
        listOfDouble.add(2.0400);
        boolean isDetailingMath = true;

        String outputNumber = "0.04";

        double expected = 2.0800;

        double actual = ArithmeticClickHelper.plusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void plusClickButtonBlankListBlankOutputNumber() {
        List<Double> listOfDouble = new ArrayList<>();
        boolean isDetailingMath = true;

        String outputNumber = null;

        double expected = 0;

        double actual = ArithmeticClickHelper.plusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void plusClickButtonBlankListOneOutputNumber() {
        List<Double> listOfDouble = new ArrayList<>();
        boolean isDetailingMath = true;

        String outputNumber = "11.02";

        double expected = 0;

        double actual = ArithmeticClickHelper.plusButtonClick(listOfDouble, outputNumber, isDetailingMath);

        assertEquals(expected, actual, deltaValue);
    }
}
