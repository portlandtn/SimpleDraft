package com.jedmay.simpledraft.helper;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Converters {

    @TypeConverter
    public static String doubleToString(List<Double> values) {
        StringBuilder returnValue = new StringBuilder();

        if (values.size() <= 0) {
            returnValue.append("0.0");
        };
        try {
            if (values.size() > 1) {
                for (int i = 0; i < values.size() - 1; i++) {
                    returnValue.append(values.get(i)).append(",");
                }
                returnValue.append(values.get(values.size() - 1));
            } else {
                returnValue.append(values.get(0));
            }
        } catch (NullPointerException ex) {
            returnValue.append("0.0");
        }

        return returnValue.toString();
    }

    @TypeConverter
    public static List<Double> stringToListOfDouble(String values) {
        if (values == null) {
            return null;
        }
        String[] arrayValues = values.split(",");
        List<Double> valuesDoubleList = new ArrayList<>();
        try {
            for (String number : arrayValues) {
                valuesDoubleList.add(Double.parseDouble(number));
            }
        } catch (NumberFormatException ex) {
            Log.d("stringToListDouble", Objects.requireNonNull(ex.getLocalizedMessage()));

        }
        return valuesDoubleList;
    }

    public static double extractWholeNumberFromDimension(double dimension) {
        return (int) dimension;
    }

    public static double extractInchesFromDimension(double dimension) {

        // Remove whole feet:
        dimension -= extractWholeNumberFromDimension(dimension);

        //convert inch from decimal to whole inch:
        dimension *= 100;

        //Truncate sixteenths off
        double returnValue = (int) dimension;
        return returnValue / 100;

    }

    public static double extractSixteenthsFromDimension(double dimension) {

        // Remove whole feet
        dimension -= extractWholeNumberFromDimension(dimension);

        //convert inch from decimal to whole inch:
        dimension *= 100;

        // Remove whole inches
        dimension -= extractWholeNumberFromDimension(dimension);

        // convert sixteenths back to thousandths place
        dimension /= 100;

        return dimension;

    }

    public static double footDimensionToDecimalDimension(double footDimension) {
        double feet = extractWholeNumberFromDimension(footDimension);
        double inches = (footDimension - feet)*100;
        double sixteenths = inches;
        inches = (int) inches;
        sixteenths -= inches;
        inches /= 12;
        sixteenths *=  100;
        sixteenths /= 16;
        sixteenths /= 12;

        return feet + inches + sixteenths;
    }

    public static double decimalDimensionToFootDimension(double decimalDimension) {
        double feet = extractWholeNumberFromDimension(decimalDimension);
        double inches = (decimalDimension - feet) * 12;
        double sixteenths = inches;
        inches = round(inches, 1);
        inches = (int) inches;
        sixteenths -= inches;
        inches /= 100;
        sixteenths *= 16;
        sixteenths /= 10000;

        double answer =  feet + inches + sixteenths;
        // this method MUST return 4-digit decimal because of the way the conversions take place later.
        return round(answer, 4);
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        double result = Math.round(value*scale + Math.signum(value)*0.1);
        result = result / scale;
        return result;
    }

    public static double[] listOfDoubleToDoubleArray(List<Double> doubleList) {
        double[] angleArray = new double[doubleList.size()];
        for (int i = 0; i < doubleList.size(); i++) {
            angleArray[i] = doubleList.get(i);
        }
        return angleArray;
    }

    public static double baseRiseToAngle(double base, double rise) {
        double response = rise / base;
        response = Math.atan(response);
        return Math.toDegrees(response);
    }
}
