package com.jedmay.simpledraft.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.jedmay.simpledraft.model.OutputState;

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

    public static OutputState getOutputState(Context context) {
        OutputState state = new OutputState();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Save New State");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> state.setName(input.getText().toString()));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
        return state;
    }

    public static int getNumberOfValuesToRemoveFromList(String outputNumber) {
        return outputNumber.isEmpty() ? 2 : 1;
    }
}
