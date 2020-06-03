package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.Converters;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ConvertersUnitTests {

    private double deltaValue = 0.0001;

    @Test
    public void convertListDoubleToString() {

        List<Double> values = new ArrayList<>();
        values.add(14.0804);
        values.add(12.0902);
        values.add(-14.0104);

        String actual = Converters.doubleToString(values);
        String expected = "14.0804,12.0902,-14.0104";

        assertEquals(expected,actual);
    }

    @Test
    public void convertStringToListDouble() {

        String values = "14.0804,12.0902,-14.0104";
        List<Double> actual = Converters.stringToListOfDouble(values);

        List<Double> doubleValues = new ArrayList<>();
        doubleValues.add(14.0804);
        doubleValues.add(12.0902);
        doubleValues.add(-14.0104);

        assertEquals(doubleValues,actual);

    }

    @Test
    public void timeStampToDateUnitTest() {
        Calendar c = Calendar.getInstance();
        c.set(2020,5,22,8,5,8);
        c.set(Calendar.MILLISECOND,0);
        Date date = new Date();
        date.setTime(c.getTimeInMillis());

        long expected = 1592831108000L;

        long actual = Converters.dateToTimestamp(date);

        assertEquals(expected, actual);
    }

    @Test
    public void timeStampToDateUnitTestWithNullDate() {
        Date date = null;
        Long expected = null;

        Long actual = Converters.dateToTimestamp(date);

        assertEquals(expected, actual);
    }

    @Test
    public void dateToTimeStampUnitTest() {

        long timeInMills = 1592831108000L;

        Calendar c = Calendar.getInstance();
        c.set(2020,5,22,8,5,8);
        c.set(Calendar.MILLISECOND,0);
        Date expected = new Date();
        expected.setTime(c.getTimeInMillis());

        Date actual = Converters.timestampToDate(timeInMills);

        assertEquals(expected, actual);
    }

    @Test
    public void dateToTimeStampUnitTestWithNull() {
        Long timeInMills = null;
        Date expected = null;

        Date actual = Converters.timestampToDate(timeInMills);

        assertEquals(expected, actual);
    }

    @Test
    public void formatDateUnitTest() {
        Calendar c = Calendar.getInstance();
        c.set(2020,5,22,8,5,8);
        c.set(Calendar.MILLISECOND,0);
        Date date = new Date();
        date.setTime(c.getTimeInMillis());

        String expected = "6/22/20";

        String actual = Converters.formatDate(date);

        assertEquals(expected, actual);
    }

    @Test
    public void formatDateUnitTestWithNull() {
        Date date = null;

        String expected = "";

        String actual = Converters.formatDate(date);

        assertEquals(expected, actual);
    }

    @Test
    public void extractFeetFromDimension() {
        double dimension = 14.0204;

        double expected = 14;
        double actual = Converters.extractWholeNumberFromDimension(dimension);

        assertEquals(expected,actual,0);
    }

    @Test
    public void extractFeetFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -14;
        double actual = Converters.extractWholeNumberFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);
    }

    @Test
    public void extractInchesFromDimension() {
        double dimension = 14.0204;

        double expected = 0.02;

        double actual = Converters.extractInchesFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void extractInchesFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -0.02;

        double actual = Converters.extractInchesFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void extractSixteenthsFromDimension() {
        double dimension = 14.0204;

        double expected = 0.0004;

        double actual = Converters.extractSixteenthsFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void extractSixteenthsFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -0.0004;

        double actual = Converters.extractSixteenthsFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void convertFootDimensionToDecimalDimension() {
        double footDimension = 12.0204;
        double expected = 12.1875;

        double actual = Converters.footDimensionToDecimalDimension(footDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertFootDimensionToDecimalDimensionOnlyInches() {
        double footDimension = 0.01;
        double expected = 0.083333;

        double actual = Converters.footDimensionToDecimalDimension(footDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertFootDimensionToDecimalDimensionOnlySixteenths() {
        double footDimension = 0.0001;
        double expected = 0.005208;

        double actual = Converters.footDimensionToDecimalDimension(footDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertFootDimensionToDecimalDimensionNegativeValue() {
        double footDimension = -12.0204;
        double expected = -12.1875;

        double actual = Converters.footDimensionToDecimalDimension(footDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertDecimalDimensionToFootDimension() {
        double decimalDimension = 12.1875;
        double expected = 12.0204;

        double actual = Converters.decimalDimensionToFootDimension(decimalDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertDecimalDimensionToFootDimensionSmallNumber() {
        double decimalDimension = 0.796875;
        double expected = 0.0909;

        double actual = Converters.decimalDimensionToFootDimension(decimalDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertDecimalDimensionToFootDimensionNegativeNumber() {
        double decimalDimension = -12.1875;
        double expected = -12.0204;

        double actual = Converters.decimalDimensionToFootDimension(decimalDimension);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void roundTest() {
        double numberToRound = 1.04082234089234;
        int precision = 4;

        double expected = 1.0408;

        double actual = Converters.round(numberToRound, precision);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void roundTest5Decimals() {
        double numberToRound = 1.040899934089234;
        int precision = 5;

        double expected = 1.0409;

        double actual = Converters.round(numberToRound, precision);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void roundTest1Decimal() {
        double numberToRound = -15691.790899934089234;
        int precision = 1;

        double expected = -15691.8;

        double actual = Converters.round(numberToRound, precision);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void roundTest4Decimals() {
        double numberToRound = 9.03085;
        int precision = 4;

        double expected = 9.0309;

        double actual = Converters.round(numberToRound, precision);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertListOfDoubleToDoubleArray() {
        List<Double> doubleList = new ArrayList<>(4);
        doubleList.add(14.0208);
        doubleList.add(1.1935);
        doubleList.add(11.2222);
        doubleList.add(2.3859);

        double[] expected = new double[doubleList.size()];
        expected[0] = 14.0208;
        expected[1] = 1.1935;
        expected[2] = 11.2222;
        expected[3] = 2.3859;

        double[] actual = Converters.listOfDoubleToDoubleArray(doubleList);

        assertArrayEquals(expected, actual, 0.0);
    }

    @Test
    public void convertBaseRiseToAngle() {
        double base = 1.0;
        double rise = 0.0833333333;

        double expected = 4.7636;

        double actual = Converters.baseRiseToAngle(base, rise);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void convertBaseRiseToAngle2On12() {
        double base = 1.0;
        double rise = 0.166666667;

        double expected = 9.4623;

        double actual = Converters.baseRiseToAngle(base, rise);

        assertEquals(expected, actual, deltaValue);
    }
}