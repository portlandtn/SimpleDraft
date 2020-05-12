package com.jedmay.simpledraft.helper;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converters {


    @TypeConverter
    public static String doubleToString(List<Double> values) {
        StringBuilder returnValue = new StringBuilder();

        if (values.size() == 0) return "";

        if (values.size() > 1) {
            for(int i = 0; i < values.size() - 1; i++) {
                returnValue.append(values.get(i)).append(",");
            }
            returnValue.append(values.get(values.size()-1));
        } else {
            returnValue.append(values.get(0));
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
        for (String number : arrayValues) {
            valuesDoubleList.add(Double.parseDouble(number));
        }
        return valuesDoubleList;
    }

}
