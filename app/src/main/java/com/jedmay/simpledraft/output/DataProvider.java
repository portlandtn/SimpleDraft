package com.jedmay.simpledraft.output;

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


}
