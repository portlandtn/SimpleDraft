package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.Converters;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConvertersUnitTests {

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

    private double deltaValue = 0.000001;

    @Test
    public void ExtractFeetFromDimension() {
        double dimension = 14.0204;

        double expected = 14;
        double actual = Converters.extractWholeNumberFromDimension(dimension);

        assertEquals(expected,actual,0);
    }

    @Test
    public void ExtractFeetFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -14;
        double actual = Converters.extractWholeNumberFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);
    }

    @Test
    public void ExtractInchesFromDimension() {
        double dimension = 14.0204;

        double expected = 0.02;

        double actual = Converters.extractInchesFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void ExtractInchesFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -0.02;

        double actual = Converters.extractInchesFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void ExtractSixteenthsFromDimension() {
        double dimension = 14.0204;

        double expected = 0.0004;

        double actual = Converters.extractSixteenthsFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void ExtractSixteenthsFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -0.0004;

        double actual = Converters.extractSixteenthsFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }
}