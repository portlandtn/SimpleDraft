package com.jedmay.simpledraft.helper;

import com.jedmay.simpledraft.R;

import java.util.ResourceBundle;

public class Arithmetic {
    public static double add(double num1, double num2, boolean isDetailingMath) {
        if (!isDetailingMath) {
            return  num1 + num2;
        } else {
            num1 = Converters.footDimensionToDecimalDimension(num1);
            num2 = Converters.footDimensionToDecimalDimension(num2);
            double response = num1 + num2;
            return Converters.decimalDimensionToFootDimension(response);
        }
    }

    public static double subtract(double num1, double num2, boolean isDetailingMath) {
        if (!isDetailingMath) {
            return  num1 - num2;
        } else {
            num1 = Converters.footDimensionToDecimalDimension(num1);
            num2 = Converters.footDimensionToDecimalDimension(num2);
            double response = num1 - num2;
            return Converters.decimalDimensionToFootDimension(response);
        }
    }

    public static double multiply(double num1, double num2, boolean isDetailingMath) {
        if (!isDetailingMath) {
            return  num1 * num2;
        } else {
            num1 = Converters.footDimensionToDecimalDimension(num1);
            num2 = Converters.footDimensionToDecimalDimension(num2);
            double response = num1 * num2;
            return Converters.decimalDimensionToFootDimension(response);
        }
    }

    public static double divide(double num1, double num2, boolean isDetailingMath) {
        if (!isDetailingMath) {
            return  num1 / num2;
        } else {
            num1 = Converters.footDimensionToDecimalDimension(num1);
            num2 = Converters.footDimensionToDecimalDimension(num2);
            double response = num1 / num2;
            return Converters.decimalDimensionToFootDimension(response);
        }
    }
}
