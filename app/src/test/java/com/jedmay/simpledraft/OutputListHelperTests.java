package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.OutputListHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class OutputListHelperTests {

    @Test
    public void addNumberToListForOutput() {

        List<String> emptyWindow = new ArrayList<>();
        StringBuilder newBuiltString = new StringBuilder();
        newBuiltString.append(1);
        newBuiltString.append(2);
        newBuiltString.append(".");
        newBuiltString.append(0);
        newBuiltString.append(2);
        newBuiltString.append(0);
        newBuiltString.append(4);

        List<String> newWindow;

        newWindow = (OutputListHelper.addValueToStringOutput(emptyWindow,newBuiltString));
        List<String> expected = new ArrayList<>();
        expected.add("12.0204");

        assertEquals(newWindow, expected);

    }

}
