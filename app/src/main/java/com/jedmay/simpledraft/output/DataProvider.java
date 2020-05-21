package com.jedmay.simpledraft.output;

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<Double> getValueFromEnterKeyPress(String outputNumber, List<Double> listOfDouble) {
        if (outputNumber.length() > 0) {
            try {
                listOfDouble.add(Double.parseDouble(outputNumber));
            } catch (NullPointerException ex) {
                System.out.println(ex.getLocalizedMessage() + " | " + DataProvider.class);
                listOfDouble = new ArrayList<>();
                listOfDouble.add(Double.parseDouble(outputNumber));
            }
        } else {
            try {
                listOfDouble.add(listOfDouble.get(listOfDouble.size() - 1));
            } catch (NullPointerException ex) {
                System.out.println(ex.getLocalizedMessage() + " | " + DataProvider.class);
                listOfDouble = new ArrayList<>();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println(ex.getLocalizedMessage() + " | " + DataProvider.class);
                listOfDouble = new ArrayList<>();
            }
        }
        return listOfDouble;
    }


    public static List<Double> getValuesForArithmetic(String outputNumber, List<Double> listOfDouble) {

        List<Double> response = new ArrayList<>();

        if (listOfDouble.size() < 1 || listOfDouble.size() == 1 && outputNumber.length() == 0) {
            return null;
        }

        if (outputNumber.length() == 0) {
            response.add(listOfDouble.get(listOfDouble.size() - 2));
            response.add(listOfDouble.get(listOfDouble.size() - 1));
        }

        if (outputNumber.length() > 0) {
            response.add(listOfDouble.get(listOfDouble.size() - 1));
            response.add(Double.parseDouble(outputNumber));
        }

        return response;
    }

    public static Double getValueForTrig(String outputNumber, List<Double> listOfDouble) {
        if (outputNumber.length() > 0) {
            return Double.parseDouble(outputNumber);
        } else if (listOfDouble.size() > 0) {
            return listOfDouble.get(listOfDouble.size() - 1);
        } else {
            return null;
        }
    }

    public static List<Double> formatListAfterArithmetic(boolean outputNumberUsedInCalc, List<Double> listOfDouble, double arithmeticAnswer) {
        listOfDouble.remove(listOfDouble.size() - 1);
        if (!outputNumberUsedInCalc) {
            listOfDouble.remove(listOfDouble.size() - 1);
        }
        listOfDouble.add(arithmeticAnswer);
        return listOfDouble;
    }

    public static List<Double> formatListAfterTrig(boolean outputNumberUsedInCalc, List<Double> listOfDouble, double arithmeticAnswer) {
        if (!outputNumberUsedInCalc) {
            listOfDouble.remove(listOfDouble.size() - 1);
        }
        listOfDouble.add(arithmeticAnswer);
        return listOfDouble;
    }
}
