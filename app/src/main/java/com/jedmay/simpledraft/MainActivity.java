package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.helper.Arithmetic;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.Converters;
import com.jedmay.simpledraft.helper.SampleDbData;
import com.jedmay.simpledraft.helper.Trig;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.helper.DataProvider;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Spinner output1Spinner, output2Spinner;
    ListView outputListView1, outputListView2;
    Button calculateWeightButton, anglesButton, deleteButton, clearButton, backspaceButton, negativePositiveButton,
            footToDecimalButton, decimalToFootButton,
            riseToSlopeButton, riseToBaseButton, baseToSlopeButton, baseToRiseButton, slopeToBaseButton, slopeToRiseButton;
    Button divideButton, multiplyButton, minusButton, plusButton, enterButton, decimalButton;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;
    Button saveState1Button, saveState2Button;
    TextView outputNumberTextView, currentRoofSlope;

    Switch mathMethod, outputWindowSwitch;

    SimpleDraftDbBadCompany db;
    SampleDbData sampleDbData;

    StringBuilder outputNumber;

    List<Double> outputNumber1List, outputNumber2List, state1Angles, state2Angles;
    double angle1, angle2, angle3, angle4;

    int activeWindow, activeAngleNumber;

    boolean isDetailingMathMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());
        sampleDbData = new SampleDbData(getApplicationContext());
        sampleDbData.populateDbWithSampleData();
        activeWindow = 1;
        activeAngleNumber = 1;
        isDetailingMathMethod = false;
        outputNumber = new StringBuilder();

        findAllViews();

        populateOutputSpinners();

        setOnClickListeners();

        setSlopeText();

    }

    private double getValueForActiveWindow(double activeWindow) {
        double value;
        try {
            if (activeWindow == 1) {
                value = DataProvider.getValueForTrig(outputNumber.toString(), outputNumber1List);
            } else {
                value = DataProvider.getValueForTrig(outputNumber.toString(), outputNumber2List);
            }
        } catch (NullPointerException ex) {
            Log.d("riseToBase", Objects.requireNonNull(ex.getLocalizedMessage()));
            value = 0;
        }
        return value;
    }

    private void saveNewState(List<Double> numberList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Save New State");

        final EditText input = new EditText(getApplicationContext());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            OutputState newState1 = new OutputState();
            newState1.setName(input.getText().toString());
            newState1.setValues(numberList);
            newState1.setAngle1(state1Angles.get(0));
            newState1.setAngle2(state1Angles.get(1));
            newState1.setAngle3(state1Angles.get(2));
            newState1.setAngle4(state1Angles.get(3));
            db.outputStateDao().insert(newState1);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private static void deleteOutputState(OutputState state, SimpleDraftDbBadCompany db, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete this entry?");
        alertDialogBuilder.setPositiveButton("Delete",
                (arg0, arg1) -> db.outputStateDao().delete(state));
        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
            //do nothing
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    // region Update Controls
    private void updateListView(ListView listView, List<Double> values) {
        String[] listStringArray = new String[values.size()];

        for (int i = 0; i < values.size(); i++) {
            listStringArray[i] = values.get(i).toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_layout, R.id.listViewItem, listStringArray);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        outputNumber.setLength(0);
        outputNumberTextView.setText("");
    }

    @SuppressLint("DefaultLocale")
    private void updateRadioButtonValues(List<Double> angles) {

        String[] angleStringArray = new String[angles.size()];

        for (int i = 0; i < angles.size(); i++) {
            angleStringArray[i] = String.format("%.4f", angles.get(i));
        }
        angle1RadioButton.setText(angleStringArray[0]);
        angle2RadioButton.setText(angleStringArray[1]);
        angle3RadioButton.setText(angleStringArray[2]);
        angle4RadioButton.setText(angleStringArray[3]);

    }

    private void updateListViewWithValueFromTrig(double value) {
        if (outputNumber.toString().isEmpty()) {
            switch (activeWindow) {
                case 1:
                    outputNumber1List.set(outputNumber1List.size() - 1, value);
                    break;
                case 2:
                    outputNumber2List.set(outputNumber2List.size() - 1, value);
                    break;
                default:
                    break;
            }
        } else {
            switch (activeWindow) {
                case 1:
                    outputNumber1List.add(value);
                    break;
                case 2:
                    outputNumber2List.add(value);
                    break;
                default:
                    break;
            }
        }
        outputNumber.setLength(0);
        updateListView(outputListView1, outputNumber1List);
        updateListView(outputListView2, outputNumber2List);
    }

    private void setSlopeText() {
        switch (activeAngleNumber) {
            case 1:
                setRoofSlopeText(Double.parseDouble(angle1RadioButton.getText().toString()));
                break;
            case 2:
                setRoofSlopeText(Double.parseDouble(angle2RadioButton.getText().toString()));
                break;
            case 3:
                setRoofSlopeText(Double.parseDouble(angle3RadioButton.getText().toString()));
                break;
            case 4:
                setRoofSlopeText(Double.parseDouble(angle4RadioButton.getText().toString()));
                break;
            default:
                setRoofSlopeText(0.0);
                break;
        }

    }

    private void setRoofSlopeText(Double angle) {
        double roofSlope = Trig.getRoofSlopeFromAngle(angle);
        currentRoofSlope.setText(String.valueOf(Converters.round(roofSlope, 4)));
    }

    // endregion

    // region OnClickListeners

    private void setOnClickListeners() {

        Button[] numberButtons = new Button[]{zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton};
        setNumberButtonOnClickListener(numberButtons);

        setDecimalButtonOnClickListener();
        setOutputSpinnerOnClickListeners();
        setOutputSpinnerSaveButtonOnClickListeners();
        setArithmeticButtonOnClickListeners();
        setTrigonometryButtonOnClickListeners();
        setActivityButtonOnClickListeners();
        setGeneralButtonOnClickListeners();
        setRadioButtonOnClickListeners();
        setSwitchOnClickListeners();

    }

    private void setNumberButtonOnClickListener(Button[] numberButtons) {

        for (int i = 0; i < numberButtons.length; i++) {
            final int finalI = i;
            numberButtons[i].setOnClickListener(v -> {
                if (outputNumber.length() == 0) {
                    outputNumber = new StringBuilder();
                }
                outputNumber.append(finalI);
                outputNumberTextView.setText(outputNumber);
            });
        }
    }

    private void setDecimalButtonOnClickListener() {
        decimalButton.setOnClickListener(v -> {
            if (outputNumber.length() == 0) {
                outputNumber = new StringBuilder();
                outputNumber.append("0.");
            } else if (outputNumber.toString().contains(".")) {
                Log.d("decimalHitTwice","Decimal button was hit twice");
                // do nothing, the decimal is already there, so button press should be ignored
            } else {
                outputNumber.append(".");
                outputNumberTextView.setText(outputNumber.toString());
            }
        });
    }

    private void setOutputSpinnerOnClickListeners() {

        output1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = output1Spinner.getSelectedItem().toString();

                if (selected.equals(Constants.newSave)) {
                    saveNewState(outputNumber1List);
                } else {
                    OutputState state = db.outputStateDao().getOutputStateFromName(selected);
                    outputNumber1List = state.getValues();
                    updateListView(outputListView1, outputNumber1List);
                    state1Angles = Trig.updateAngles(state1Angles, state.getAngle1(), state.getAngle2(), state.getAngle3(), state.getAngle4());
                    updateRadioButtonValues(state1Angles);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        output1Spinner.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteOutputState(db.outputStateDao().getOutputStateFromName(output1Spinner.getSelectedItem().toString()), db, this);
            return false;
        });

        output2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = output2Spinner.getSelectedItem().toString();

                if (selected.equals(Constants.newSave)) {
                    saveNewState(outputNumber2List);
                } else {
                    OutputState state = db.outputStateDao().getOutputStateFromName(selected);
                    outputNumber2List = state.getValues();
                    updateListView(outputListView2, outputNumber2List);
                    state2Angles = Trig.updateAngles(state2Angles, state.getAngle1(), state.getAngle2(), state.getAngle3(), state.getAngle4());
                    updateRadioButtonValues(state2Angles);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        output2Spinner.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteOutputState(db.outputStateDao().getOutputStateFromName(output2Spinner.getSelectedItem().toString()), db, this);
            return false;
        });
    }

    private void setOutputSpinnerSaveButtonOnClickListeners() {
        saveState1Button.setOnClickListener(v -> {
            OutputState state;
            boolean newSave = output1Spinner.getSelectedItem().toString().equals(Constants.newSave);

            if (newSave) {
                state = DataProvider.getOutputState(this);
            } else {
                state = db.outputStateDao().getOutputStateFromName(output1Spinner.getSelectedItem().toString());
            }
            state.setAngle1(state1Angles.get(0));
            state.setAngle2(state1Angles.get(1));
            state.setAngle3(state1Angles.get(2));
            state.setAngle4(state1Angles.get(3));
            state.setValues(outputNumber1List);

            if (newSave) {
                db.outputStateDao().insert(state);
            } else {
                db.outputStateDao().update(state);
            }
        });

        saveState2Button.setOnClickListener(v -> {
            OutputState state;
            boolean newSave = output2Spinner.getSelectedItem().toString().equals(Constants.newSave);

            if (newSave) {
                state = DataProvider.getOutputState(getApplicationContext());
            } else {
                state = db.outputStateDao().getOutputStateFromName(output2Spinner.getSelectedItem().toString());
            }
            state.setAngle1(state2Angles.get(0));
            state.setAngle2(state2Angles.get(1));
            state.setAngle3(state2Angles.get(2));
            state.setAngle4(state2Angles.get(3));
            state.setValues(outputNumber2List);

            if (newSave) {
                db.outputStateDao().insert(state);
            }
            else {
                db.outputStateDao().update(state);
            }
        });
    }

    private void setArithmeticButtonOnClickListeners() {
        minusButton.setOnClickListener(v -> {
            List<Double> mathValues;
            mathValues = DataProvider.getValuesForArithmetic(outputNumber.toString(), outputNumber1List);

            switch (activeWindow) {
                case 1:
                    outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber1List.add(Arithmetic.subtract(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber2List.add(Arithmetic.subtract(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }
        });
        plusButton.setOnClickListener(v -> {
            List<Double> mathValues;
            mathValues = DataProvider.getValuesForArithmetic(outputNumber.toString(), outputNumber1List);

            switch (activeWindow) {
                case 1:
                    outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber1List.add(Arithmetic.add(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber2List.add(Arithmetic.add(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }

        });
        divideButton.setOnClickListener(v -> {
            List<Double> mathValues;
            mathValues = DataProvider.getValuesForArithmetic(outputNumber.toString(), outputNumber1List);

            switch (activeWindow) {
                case 1:
                    outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber1List.add(Arithmetic.divide(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber2List.add(Arithmetic.divide(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }
        });
        multiplyButton.setOnClickListener(v -> {
            List<Double> mathValues;
            mathValues = DataProvider.getValuesForArithmetic(outputNumber.toString(), outputNumber1List);

            switch (activeWindow) {
                case 1:
                    outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber1List.add(Arithmetic.multiply(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1));
                    try {
                        if (mathValues != null) {
                            outputNumber2List.add(Arithmetic.multiply(mathValues.get(0), mathValues.get(1), isDetailingMathMethod));
                        }
                    } catch (NullPointerException ex) {
                        Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                    }
                    outputNumber.setLength(0);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }
        });
    }

    private void setTrigonometryButtonOnClickListeners() {
        riseToBaseButton.setOnClickListener(v -> {
            double value;
            value = getValueForActiveWindow(activeWindow);

            if (isDetailingMathMethod) {
                value = Converters.footDimensionToDecimalDimension(value);
            }

            Trig.riseToBase(value, activeAngleNumber);

            if (isDetailingMathMethod) {
                value = Converters.decimalDimensionToFootDimension(value);
            }
            updateListViewWithValueFromTrig(value);

        });

        riseToSlopeButton.setOnClickListener(v -> {
            double value;
            value = getValueForActiveWindow(activeWindow);

            if (isDetailingMathMethod) {
                value = Converters.footDimensionToDecimalDimension(value);
            }

            Trig.riseToSlope(value, activeAngleNumber);

            if (isDetailingMathMethod) {
                value = Converters.decimalDimensionToFootDimension(value);
            }
            updateListViewWithValueFromTrig(value);
        });

        baseToRiseButton.setOnClickListener(v -> {
            double value;
            value = getValueForActiveWindow(activeWindow);

            if (isDetailingMathMethod) {
                value = Converters.footDimensionToDecimalDimension(value);
            }

            Trig.baseToRise(value, activeAngleNumber);

            if (isDetailingMathMethod) {
                value = Converters.decimalDimensionToFootDimension(value);
            }
            updateListViewWithValueFromTrig(value);
        });

        baseToSlopeButton.setOnClickListener(v -> {
            double value;
            value = getValueForActiveWindow(activeWindow);

            if (isDetailingMathMethod) {
                value = Converters.footDimensionToDecimalDimension(value);
            }

            Trig.baseToSlope(value, activeAngleNumber);

            if (isDetailingMathMethod) {
                value = Converters.decimalDimensionToFootDimension(value);
            }
            updateListViewWithValueFromTrig(value);
        });

        slopeToBaseButton.setOnClickListener(v -> {
            double value;
            value = getValueForActiveWindow(activeWindow);

            if (isDetailingMathMethod) {
                value = Converters.footDimensionToDecimalDimension(value);
            }

            Trig.slopeToBase(value, activeAngleNumber);

            if (isDetailingMathMethod) {
                value = Converters.decimalDimensionToFootDimension(value);
            }
            updateListViewWithValueFromTrig(value);
        });

        slopeToRiseButton.setOnClickListener(v -> {
            double value;
            value = getValueForActiveWindow(activeWindow);

            if (isDetailingMathMethod) {
                value = Converters.footDimensionToDecimalDimension(value);
            }

            Trig.slopeToRise(value, activeAngleNumber);

            if (isDetailingMathMethod) {
                value = Converters.decimalDimensionToFootDimension(value);
            }
            updateListViewWithValueFromTrig(value);
        });


    }

    private void setActivityButtonOnClickListeners() {
        calculateWeightButton.setOnClickListener(v -> {
            //TODO Must Develop fragment to display weight input
        });

        anglesButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AngleCalculatorActivity.class);

            Bundle extras = new Bundle();
            extras.putDoubleArray("state1Angles", Converters.listOfDoubleToDoubleArray(state1Angles));
            extras.putDoubleArray("state2Angles", Converters.listOfDoubleToDoubleArray(state2Angles));

            i.putExtras(extras);
            startActivity(i);

        });
    }

    private void setGeneralButtonOnClickListeners() {
        deleteButton.setOnClickListener(v -> {
            switch (activeWindow) {
                case 1:
                    if (outputNumber1List.size() > 0) {
                        outputNumber1List.remove(outputNumber1List.size() - 1);
                        updateListView(outputListView1, outputNumber1List);
                    }
                    break;
                case 2:
                    if (outputNumber2List.size() > 0) {
                        outputNumber2List.remove(outputNumber2List.size() - 1);
                        updateListView(outputListView2, outputNumber2List);
                    }
                    break;
                default:
                    break;
            }
        });

        clearButton.setOnClickListener(v -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to clear the entire screen for window " + activeWindow + "?");
            alertDialogBuilder.setPositiveButton("Delete", (dialog, which) -> {
                switch (activeWindow) {
                    case 1:
                        outputNumber1List.clear();
                        updateListView(outputListView1, outputNumber1List);
                        break;
                    case 2:
                        outputNumber2List.clear();
                        updateListView(outputListView2, outputNumber2List);
                        break;
                    default:
                        break;
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                // do nothing
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        backspaceButton.setOnClickListener(v -> {
            if (outputNumber.length() > 0) {
                outputNumber.deleteCharAt(outputNumber.length() - 1);
            }
            outputNumberTextView.setText(outputNumber.toString());
        });

        enterButton.setOnClickListener(v -> {

            switch (activeWindow) {
                case 1:
                    outputNumber1List = DataProvider.getValueFromEnterKeyPress(outputNumber.toString(), outputNumber1List);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List = DataProvider.getValueFromEnterKeyPress(outputNumber.toString(), outputNumber2List);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }
            outputNumber.setLength(0);
            outputNumberTextView.setText(outputNumber.toString());
        });

        negativePositiveButton.setOnClickListener(v -> {
            double changeUp;
            switch (activeWindow) {
                case 1:
                    changeUp = outputNumber1List.get(outputNumber1List.size() - 1);
                    changeUp *= -1;
                    outputNumber1List.set(outputNumber1List.size() - 1, changeUp);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    changeUp = outputNumber2List.get(outputNumber2List.size() - 1);
                    changeUp *= -1;
                    outputNumber2List.set(outputNumber2List.size() - 1, changeUp);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }
        });
    }

    private void setRadioButtonOnClickListeners() {

        angle1RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(Double.parseDouble(angle1RadioButton.getText().toString()));
            activeAngleNumber = 1;
        });
        angle2RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(Double.parseDouble(angle2RadioButton.getText().toString()));
            activeAngleNumber = 2;
        });
        angle3RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(Double.parseDouble(angle3RadioButton.getText().toString()));
            activeAngleNumber = 3;
        });
        angle4RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(Double.parseDouble(angle4RadioButton.getText().toString()));
            activeAngleNumber = 4;
        });

    }

    private void setSwitchOnClickListeners() {
        mathMethod.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDetailingMathMethod = mathMethod.isChecked();
            mathMethod.setText(mathMethod.isChecked() ? "Detailing" : "Standard");
        });

        outputWindowSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activeWindow = outputWindowSwitch.isChecked() ? 2 : 1;
            if (activeWindow == 1) {
                outputWindowSwitch.setText(R.string.window_1);
                outputListView1.setBackgroundColor(getResources().getColor(R.color.activeBackground));
                outputListView2.setBackgroundColor(getResources().getColor(R.color.inactiveBackground));
            } else {
                outputWindowSwitch.setText(R.string.window_2);
                outputListView2.setBackgroundColor(getResources().getColor(R.color.activeBackground));
                outputListView1.setBackgroundColor(getResources().getColor(R.color.inactiveBackground));
            }
        });
    }

    // endregion

    // region Actions For On Create
    private void populateOutputSpinners() {
        try {
            List<OutputState> states = db.outputStateDao().getAllOutputStates();
            String[] stateArray = new String[states.size() + 1];

            for(int i = 0; i < states.size(); i++) {
                stateArray[i] = states.get(i).getName();
            }

            stateArray[states.size()] = Constants.newSave;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, R.layout.spinner_view_layout, R.id.spinnerViewItem, stateArray
            );
            adapter.setDropDownViewResource(R.layout.spinner_view_layout);
            output1Spinner.setAdapter(adapter);
            output2Spinner.setAdapter(adapter);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("pop_index", Objects.requireNonNull(ex.getLocalizedMessage()));
        } catch (Exception ex) {
            Log.d("pop_general", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
    }

    // endregion

    // region findAllViews
    private void findAllViews() {
        // Radio buttons
        angle1RadioButton = findViewById(R.id.angle1RadioButton);
        angle2RadioButton = findViewById(R.id.angle2RadioButton);
        angle3RadioButton = findViewById(R.id.angle3RadioButton);
        angle4RadioButton = findViewById(R.id.angle4RadioButton);

        // Switches
        mathMethod = findViewById(R.id.mathMethodSwitch);
        outputWindowSwitch = findViewById(R.id.outputWindowSwitch);

        // Spinners
        output1Spinner = findViewById(R.id.output1NameSpinner);
        output2Spinner = findViewById(R.id.output2NameSpinner);

        // List Views
        outputListView1 = findViewById(R.id.output1ListView);
        outputListView2 = findViewById(R.id.output2ListView);

        // Save Buttons
        saveState1Button = findViewById(R.id.saveState1Button);
        saveState2Button = findViewById(R.id.saveState2Button);

        // Buttons
        calculateWeightButton = findViewById(R.id.calculateWeightButton);
        anglesButton = findViewById(R.id.anglesButton);
        deleteButton = findViewById(R.id.deleteButton);
        clearButton = findViewById(R.id.clearButton);
        backspaceButton = findViewById(R.id.backspaceButton);
        negativePositiveButton = findViewById(R.id.negativePositiveButton);

        // Conversion Buttons
        footToDecimalButton = findViewById(R.id.footToDecimal);
        decimalToFootButton = findViewById(R.id.decimalToFoot);

        // Trig Buttons
        riseToSlopeButton = findViewById(R.id.riseToSlopeButton);
        riseToBaseButton = findViewById(R.id.riseToBaseButton);
        baseToSlopeButton = findViewById(R.id.baseToSlopeButton);
        baseToRiseButton = findViewById(R.id.baseToRiseButton);
        slopeToBaseButton = findViewById(R.id.slopeToRiseButton);
        slopeToRiseButton = findViewById(R.id.slopeToBaseButton);

        // Math Buttons
        divideButton = findViewById(R.id.divideButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        minusButton = findViewById(R.id.minusButton);
        plusButton = findViewById(R.id.plusButton);
        decimalButton = findViewById(R.id.decimalButton);
        enterButton = findViewById(R.id.enterButton);

        // Number Buttons
        oneButton = findViewById(R.id.oneButton);
        twoButton = findViewById(R.id.twoButton);
        threeButton = findViewById(R.id.threeButton);
        fourButton = findViewById(R.id.fourButton);
        fiveButton = findViewById(R.id.fiveButton);
        sixButton = findViewById(R.id.sixButton);
        sevenButton = findViewById(R.id.sevenButton);
        eightButton = findViewById(R.id.eightButton);
        nineButton = findViewById(R.id.nineButton);
        zeroButton = findViewById(R.id.zeroButton);

        // TextViews
        outputNumberTextView = findViewById(R.id.outputNumberTextView);
        currentRoofSlope = findViewById(R.id.currentRoofSlopeTextView);

    }

    // endregion



}
