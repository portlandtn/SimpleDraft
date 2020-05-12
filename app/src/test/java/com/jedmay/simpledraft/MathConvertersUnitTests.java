package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.MathConverters;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathConvertersUnitTests {

    private double deltaValue = 0.000001;

    @Test
    public void ExtractFeetFromDimension() {
        double dimension = 14.0204;

        double expected = 14;
        double actual = MathConverters.extractWholeNumberFromDimension(dimension);

        assertEquals(expected,actual,0);
    }

    @Test
    public void ExtractFeetFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -14;
        double actual = MathConverters.extractWholeNumberFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);
    }

    @Test
    public void ExtractInchesFromDimension() {
        double dimension = 14.0204;

        double expected = 0.02;

        double actual = MathConverters.extractInchesFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void ExtractInchesFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -0.02;

        double actual = MathConverters.extractInchesFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void ExtractSixteenthsFromDimension() {
        double dimension = 14.0204;

        double expected = 0.0004;

        double actual = MathConverters.extractSixteenthsFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

    @Test
    public void ExtractSixteenthsFromDimensionNegativeNumber() {
        double dimension = -14.0204;

        double expected = -0.0004;

        double actual = MathConverters.extractSixteenthsFromDimension(dimension);

        assertEquals(expected,actual,deltaValue);

    }

}
