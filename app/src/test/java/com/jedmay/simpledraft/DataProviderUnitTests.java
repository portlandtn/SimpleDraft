package com.jedmay.simpledraft;

import com.jedmay.simpledraft.output.DataProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataProviderUnitTests {

    @Test
    public void getValueFromEnterKeyPressNullOutputNumberNullList() {

        String outputNumber = null;
        List<Double> listOfDouble = null;

        DataProvider dataProvider = new DataProvider();

        List<Double> expected = null;
        List<Double> actual = dataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);

    }

    @Test
    public void getValueFromEnterKeyPressNullOutputNumberPopulatedList() {
        String outputNumber = null;
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(1.0408);
        listOfDouble.add(16.0308);

        DataProvider dataProvider = new DataProvider();

        List<Double> expected = listOfDouble;
        List<Double> actual = dataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressWithOutputNumberAndPopulatedList() {
        String outputNumber = "11.0204";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(1.0408);
        listOfDouble.add(16.0308);

        DataProvider dataProvider = new DataProvider();

        List<Double> expected;
        expected = listOfDouble;
        expected.add(11.0204);

        List<Double> actual = dataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressWithOutputNumberAndNullList() {
        String outputNumber = "11.0204";
        List<Double> listOfDouble = null;

        DataProvider dataProvider = new DataProvider();

        List<Double> expected = new ArrayList<>();
        expected.add(11.0204);

        List<Double> actual = dataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

}
