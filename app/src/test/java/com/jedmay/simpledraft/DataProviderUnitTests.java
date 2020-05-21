package com.jedmay.simpledraft;

import com.jedmay.simpledraft.output.DataProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataProviderUnitTests {

    @Test
    public void getValueFromEnterKeyPressNullOutputNumberBlank() {

        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();

        List<Double> expected = new ArrayList<>();
        List<Double> actual = DataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressNullOutputNumberPopulatedList() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(1.0408);
        listOfDouble.add(16.0308);

        List<Double> expected = new ArrayList<>();
        expected.add(1.0408);
        expected.add(16.0308);
        expected.add(16.0308);

        List<Double> actual = DataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressWithOutputNumberAndPopulatedList() {
        String outputNumber = "11.0204";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(1.0408);
        listOfDouble.add(16.0308);

        List<Double> expected = new ArrayList<>();
        expected.add(1.0408);
        expected.add(16.0308);
        expected.add(11.0204);

        List<Double> actual = DataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressWithOutputNumberAndNullList() {
        String outputNumber = "11.0204";
        List<Double> listOfDouble = null;

        List<Double> expected = new ArrayList<>();
        expected.add(11.0204);

        List<Double> actual = DataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressAddLastValueOfList() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0308);
        listOfDouble.add(11.0204);

        List<Double> expected = new ArrayList<>();
        expected.add(11.0308);
        expected.add(11.0204);
        expected.add(11.0204);

        List<Double> actual = DataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueFromEnterKeyPressEmptyOutputNumberSizeZeroList() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();

        List<Double> expected = new ArrayList<>();

        List<Double> actual = DataProvider.getValueFromEnterKeyPress(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

}
