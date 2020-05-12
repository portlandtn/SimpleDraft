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
}