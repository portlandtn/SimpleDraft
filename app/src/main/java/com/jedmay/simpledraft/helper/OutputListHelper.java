package com.jedmay.simpledraft.helper;

import java.util.List;

public class OutputListHelper {

    static String outputNumber;

    public static List<String> addValueToStringOutput(List<String> currentOutputNumberList, StringBuilder outputNumber) {

        currentOutputNumberList.add(outputNumber.toString());
        return currentOutputNumberList;
    }
}
