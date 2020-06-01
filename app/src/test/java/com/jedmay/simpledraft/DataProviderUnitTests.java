package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.DataProvider;
import com.jedmay.simpledraft.helper.MathType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataProviderUnitTests {

    double deltaValue = 0.0001;

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

    @Test
    public void getTwoValuesForArithmeticUsingOnlyList() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0208);
        listOfDouble.add(13.0001);
        listOfDouble.add(0.0202);

        List<Double> expected = new ArrayList<>();
        expected.add(13.0001);
        expected.add(0.0202);

        List<Double> actual = DataProvider.getValuesForArithmetic(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getTwoValuesForArithmeticUsingBlankList() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();

        List<Double> expected = null;

        List<Double> actual = DataProvider.getValuesForArithmetic(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getTwoValuesForArithmeticUsingBlankListAndOutputNumber() {
        String outputNumber = "13.0204";
        List<Double> listOfDouble = new ArrayList<>();

        List<Double> expected = null;

        List<Double> actual = DataProvider.getValuesForArithmetic(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getTwoValuesForArithmeticUsingListAndOutputNumber() {
        String outputNumber = "13.0204";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0408);

        List<Double> expected = new ArrayList<>();
        expected.add(11.0408);
        expected.add(13.0204);

        List<Double> actual = DataProvider.getValuesForArithmetic(outputNumber, listOfDouble);

        assertEquals(expected, actual);
    }

    @Test
    public void getValueForTrigonometryUsingBlankListAndNoOutputNumber() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();

        double expected = 0;

        double actual = DataProvider.getValueForTrig(outputNumber, listOfDouble);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void getValueForTrigonometryUsingListAndNoOutputNumber() {
        String outputNumber = "";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(13.0204);

        double expected = 13.0204;

        double actual = DataProvider.getValueForTrig(outputNumber, listOfDouble);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void getValueForTrigonometryUsingListAndOutputNumber() {
        String outputNumber = "11.0505";
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(13.0204);

        double expected = 11.0505;

        double actual = DataProvider.getValueForTrig(outputNumber, listOfDouble);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void getValueForTrigonometryUsingBlankListAndOutputNumber() {
        String outputNumber = "11.0505";
        List<Double> listOfDouble = new ArrayList<>();

        double expected = 11.0505;

        double actual = DataProvider.getValueForTrig(outputNumber, listOfDouble);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void formatListAfterArithmeticBlankOutputFullList() {
        boolean outputNumberUsedInCalc = false;
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0208);
        listOfDouble.add(1.0204);
        listOfDouble.add(1.0602);
        double arithmeticAnswer = 2.0806;

        List<Double> expected = new ArrayList<>();
        expected.add(11.0208);
        expected.add(2.0806);

        List<Double> actual = DataProvider.formatListAfterArithmetic(outputNumberUsedInCalc, listOfDouble, arithmeticAnswer);

        assertEquals(expected, actual);
    }

    @Test
    public void formatListAfterArithmeticWithOutputAndFullList() {
        boolean outputNumberUsedInCalc = true;
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0208);
        listOfDouble.add(1.0204);
        listOfDouble.add(1.0602);
        double arithmeticAnswer = 2.0806;

        List<Double> expected = new ArrayList<>();
        expected.add(11.0208);
        expected.add(1.0204);
        expected.add(2.0806);

        List<Double> actual = DataProvider.formatListAfterArithmetic(outputNumberUsedInCalc, listOfDouble, arithmeticAnswer);

        assertEquals(expected, actual);
    }

    @Test
    public void formatListAfterTrigWithOutputAndFullList() {
        boolean outputNumberUsedInCalc = true;
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0208);
        listOfDouble.add(1.0204);
        listOfDouble.add(1.0602);
        double arithmeticAnswer = 2.0806;

        List<Double> expected = new ArrayList<>();
        expected.add(11.0208);
        expected.add(1.0204);
        expected.add(1.0602);
        expected.add(2.0806);

        List<Double> actual = DataProvider.formatListAfterTrig(outputNumberUsedInCalc, listOfDouble, arithmeticAnswer);

        assertEquals(expected, actual);
    }

    @Test
    public void formatListAfterTrigWithBlankOutputAndFullList() {
        boolean outputNumberUsedInCalc = false;
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(11.0208);
        listOfDouble.add(1.0204);
        listOfDouble.add(1.0602);
        double arithmeticAnswer = 2.0806;

        List<Double> expected = new ArrayList<>();
        expected.add(11.0208);
        expected.add(1.0204);
        expected.add(2.0806);

        List<Double> actual = DataProvider.formatListAfterTrig(outputNumberUsedInCalc, listOfDouble, arithmeticAnswer);

        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfValuesToRemoveFromListWithArithmeticShouldReturn1() {
        String outputNumber = "11.0204";
        MathType type = MathType.ARITHMETIC;

        int expected = 1;

        int actual = DataProvider.getNumberOfValuesToRemoveFromList(outputNumber, type);

        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfValuesToRemoveFromListWithArithmeticShouldReturn2() {
        String outputNumber = "";
        MathType type = MathType.ARITHMETIC;

        int expected = 2;

        int actual = DataProvider.getNumberOfValuesToRemoveFromList(outputNumber, type);

        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfValuesToRemoveFromListWithTrigShouldReturn1() {
        String outputNumber = "";
        MathType type = MathType.TRIG;

        int expected = 1;

        int actual = DataProvider.getNumberOfValuesToRemoveFromList(outputNumber, type);

        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfValuesToRemoveFromListWithTrigShouldReturn0() {
        String outputNumber = "1.0408";
        MathType type = MathType.TRIG;

        int expected = 0;

        int actual = DataProvider.getNumberOfValuesToRemoveFromList(outputNumber, type);

        assertEquals(expected, actual);
    }

}
