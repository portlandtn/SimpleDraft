package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.Converters;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConvertersUnitTests {

    private double deltaValue = 0.000001;

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
    public void convertDecimalDimensionToFootDimensionNegativeNumber() {
        double decimalDimension = -12.1875;
        double expected = -12.0204;

        double actual = Converters.decimalDimensionToFootDimension(decimalDimension);

        assertEquals(expected, actual, deltaValue);
    }
}