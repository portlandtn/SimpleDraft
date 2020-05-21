package com.jedmay.simpledraft.helper;

import java.util.List;

public class ArithmeticClickHelper {

    public static double minusButtonClick(List<Double> listOfDouble, String outputNumber, boolean isDetailingMath) {
        if (listOfDouble.size() >= 2) {
            if (outputNumber == null || outputNumber.isEmpty()) {
                return Arithmetic.subtract(listOfDouble.get(listOfDouble.size() - 2), listOfDouble.get(listOfDouble.size() - 1),isDetailingMath);
            } else {
                return Arithmetic.subtract(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        else if (listOfDouble.size() == 1) {
            if (!(outputNumber == null)) {
                return Arithmetic.subtract(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        return 0;
    }

    public static double plusButtonClick(List<Double> listOfDouble, String outputNumber, boolean isDetailingMath) {
        if (listOfDouble.size() >= 2) {
            if (outputNumber == null || outputNumber.isEmpty()) {
                return Arithmetic.add(listOfDouble.get(listOfDouble.size() - 2), listOfDouble.get(listOfDouble.size() - 1),isDetailingMath);
            } else {
                return Arithmetic.add(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        else if (listOfDouble.size() == 1) {
            if (!(outputNumber == null)) {
                return Arithmetic.add(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        return 0;
    }

    public static double divideButtonClick(List<Double> listOfDouble, String outputNumber, boolean isDetailingMath) {
        if (listOfDouble.size() >= 2) {
            if (outputNumber == null || outputNumber.isEmpty()) {
                return Arithmetic.divide(listOfDouble.get(listOfDouble.size() - 2), listOfDouble.get(listOfDouble.size() - 1),isDetailingMath);
            } else {
                return Arithmetic.divide(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        else if (listOfDouble.size() == 1) {
            if (!(outputNumber == null)) {
                return Arithmetic.divide(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        return 0;
    }

    public static double multiplyButtonClick(List<Double> listOfDouble, String outputNumber, boolean isDetailingMath) {
        if (listOfDouble.size() >= 2) {
            if (outputNumber == null || outputNumber.isEmpty()) {
                return Arithmetic.multiply(listOfDouble.get(listOfDouble.size() - 2), listOfDouble.get(listOfDouble.size() - 1),isDetailingMath);
            } else {
                return Arithmetic.multiply(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        else if (listOfDouble.size() == 1) {
            if (!(outputNumber == null)) {
                return Arithmetic.multiply(listOfDouble.get(listOfDouble.size() - 1), Double.parseDouble(outputNumber),isDetailingMath);
            }
        }
        return 0;
    }

}
