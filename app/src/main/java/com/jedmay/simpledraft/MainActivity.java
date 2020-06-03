package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.Toast;

import com.jedmay.simpledraft.db.SimpleDraftDb;
import com.jedmay.simpledraft.helper.Arithmetic;
import com.jedmay.simpledraft.helper.ArithmeticFunction;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.Converters;
import com.jedmay.simpledraft.helper.MathType;
//import com.jedmay.simpledraft.helper.SampleDbData;
import com.jedmay.simpledraft.helper.SampleDbData;
import com.jedmay.simpledraft.helper.Trig;
import com.jedmay.simpledraft.helper.TrigFunction;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.helper.DataProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    // region fields
    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Spinner jobNumberWindow1Spinner, jobNumberWindow2Spinner;
    ListView outputListView1, outputListView2;
    Button calculateWeightButton, anglesButton, deleteButton, clearButton, backspaceButton, negativePositiveButton,
            footToDecimalButton, decimalToFootButton,
            riseToSlopeButton, riseToBaseButton, baseToSlopeButton, baseToRiseButton, slopeToBaseButton, slopeToRiseButton;
    Button divideButton, multiplyButton, minusButton, plusButton, enterButton, decimalButton;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;
    Button saveState1Button, saveState2Button, editJobsButton;
    TextView outputNumberTextView, currentRoofSlope;

    Switch mathMethod;
    String jobNumber;

    SimpleDraftDb db;
    SampleDbData sampleDbData;

    StringBuilder outputNumber;

    List<Double> outputNumber1List, outputNumber2List, state1Angles, state2Angles;

    int activeWindow, activeAngleNumber;
    // endregion

    @Override
    public void onResume() {
        super.onResume();
        populateOutputSpinners();
        populateListOfAngles(jobNumberWindow1Spinner.getSelectedItem().toString(), 1);
        populateListOfAngles(jobNumberWindow2Spinner.getSelectedItem().toString(), 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SimpleDraftDb.getDatabase(getApplicationContext());
        sampleDbData = new SampleDbData(getApplicationContext());
        sampleDbData.populateDbWithSampleData();
        activeWindow = 1;
        activeAngleNumber = 1;
        outputNumber = new StringBuilder();

        findAllViews();

        populateOutputSpinners();

        setOnClickListeners();

        setRoofSlopeTextOnStartup();

        Intent intent = getIntent();
        double weight = intent.getDoubleExtra(Constants.WEIGHT, 0);
        activeWindow = intent.getIntExtra(Constants.ACTIVE_WINDOW, 1);
        if (weight > 0) {
            switch (activeWindow) {
                case 1:
                    outputNumber1List.add(weight);
                    updateListView(outputListView1,outputNumber1List);
                    break;
                case 2:
                    outputNumber2List.add(weight);
                    updateListView(outputListView2,outputNumber2List);
                    break;
                default:
                    break;
            }
        }
        populateListOfAngles(jobNumberWindow1Spinner.getSelectedItem().toString(), 1);
        populateListOfAngles(jobNumberWindow2Spinner.getSelectedItem().toString(), 2);
    }

    private void populateListOfAngles(String stateName, int window) {
        List<Double> angles = new ArrayList<>();
        if (stateName.equals(Constants.NEW_SAVE)) {
            angles.add(0.0);
            angles.add(0.0);
            angles.add(0.0);
            angles.add(0.0);
        } else {
            angles.add(db.outputStateDao().getOutputStateFromName(stateName).getAngle1());
            angles.add(db.outputStateDao().getOutputStateFromName(stateName).getAngle2());
            angles.add(db.outputStateDao().getOutputStateFromName(stateName).getAngle3());
            angles.add(db.outputStateDao().getOutputStateFromName(stateName).getAngle4());
        }

        if (window == 1) {
            state1Angles = angles;
        } else {
            state2Angles = angles;
        }
    }

    private void doTrig(TrigFunction function) {
        double mathValue;
        double angle;
        double answer = 0;
        switch (activeWindow) {
            case 1:
                angle = Trig.getAngleForTrig(state1Angles, activeAngleNumber);
                mathValue = DataProvider.getValueForTrig(outputNumber.toString(), outputNumber1List);
                if (DataProvider.getNumberOfValuesToRemoveFromList(outputNumber.toString(), MathType.TRIG) == 1) {
                     // if outputNumber was not used in calculation, the last number should be removed from the stack.
                    outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1));
                }

                if (mathMethod.isChecked()) {
                    mathValue = Converters.footDimensionToDecimalDimension(mathValue);
                }

                try {
                    switch (function) {
                        case B2R:
                            answer = Trig.baseToRise(mathValue, angle);
                            break;
                        case B2S:
                            answer = Trig.baseToSlope(mathValue, angle);
                            break;
                        case S2B:
                            answer = Trig.slopeToBase(mathValue, angle);
                            break;
                        case S2R:
                            answer = Trig.slopeToRise(mathValue, angle);
                            break;
                        case R2B:
                            answer = Trig.riseToBase(mathValue, angle);
                            break;
                        case R2S:
                            answer = Trig.riseToSlope(mathValue, angle);
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException ex) {
                    Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                }
                if (mathMethod.isChecked()) {
                    answer = Converters.decimalDimensionToFootDimension(answer);
                }
                outputNumber1List.add(answer);
                break;
            case 2:
                angle = Trig.getAngleForTrig(state2Angles, activeAngleNumber);
                mathValue = DataProvider.getValueForTrig(outputNumber.toString(), outputNumber2List);
                if (DataProvider.getNumberOfValuesToRemoveFromList(outputNumber.toString(), MathType.TRIG) == 1) {
                    // if outputNumber was not used in calculation, then the last number should be removed from the stack.
                    outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1));
                }
                if (mathMethod.isChecked()) {
                    mathValue = Converters.footDimensionToDecimalDimension(mathValue);
                }

                try {
                    switch (function) {
                        case B2R:
                            answer = Trig.baseToRise(mathValue, angle);
                            break;
                        case B2S:
                            answer = Trig.baseToSlope(mathValue, angle);
                            break;
                        case S2B:
                            answer = Trig.slopeToBase(mathValue, angle);
                            break;
                        case S2R:
                            answer = Trig.slopeToRise(mathValue, angle);
                            break;
                        case R2B:
                            answer = Trig.riseToBase(mathValue, angle);
                            break;
                        case R2S:
                            answer = Trig.riseToSlope(mathValue, angle);
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException ex) {
                    Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                }
                if (mathMethod.isChecked()) {
                    answer = Converters.decimalDimensionToFootDimension(answer);
                }
                outputNumber2List.add(answer);
                break;
            default:
                break;
        }
        outputNumber.setLength(0);
        updateListView(outputListView1, outputNumber1List);
        updateListView(outputListView2, outputNumber2List);

    }

    private void doMath(ArithmeticFunction function) {
        List<Double> mathValues;
        switch (activeWindow) {
            case 1:
                mathValues = DataProvider.getValuesForArithmetic(outputNumber.toString(), outputNumber1List);
                outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1)); // will be removed irregardless
                if (DataProvider.getNumberOfValuesToRemoveFromList(outputNumber.toString(), MathType.ARITHMETIC) == 2) {
                    // if outputNumber was not used in calculation, then two numbers should be removed from the stack.
                    outputNumber1List.remove(outputNumber1List.get(outputNumber1List.size() - 1));
                }
                try {
                    switch (function) {
                        case ADD:
                            assert mathValues != null;
                            outputNumber1List.add(Arithmetic.add(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        case SUBTRACT:
                            assert mathValues != null;
                            outputNumber1List.add(Arithmetic.subtract(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        case MULTIPLY:
                            assert mathValues != null;
                            outputNumber1List.add(Arithmetic.multiply(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        case DIVIDE:
                            assert mathValues != null;
                            outputNumber1List.add(Arithmetic.divide(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException ex) {
                    Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                }
                break;
            case 2:
                mathValues = DataProvider.getValuesForArithmetic(outputNumber.toString(), outputNumber2List);
                outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1)); // will be removed irregardless
                if (DataProvider.getNumberOfValuesToRemoveFromList(outputNumber.toString(), MathType.ARITHMETIC) == 2) {
                    // if outputNumber was not used in calculation, then two numbers should be removed from the stack.
                    outputNumber2List.remove(outputNumber2List.get(outputNumber2List.size() - 1));
                }
                try {
                    switch (function) {
                        case ADD:
                            assert mathValues != null;
                            outputNumber2List.add(Arithmetic.add(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        case SUBTRACT:
                            assert mathValues != null;
                            outputNumber2List.add(Arithmetic.subtract(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        case MULTIPLY:
                            assert mathValues != null;
                            outputNumber2List.add(Arithmetic.multiply(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        case DIVIDE:
                            assert mathValues != null;
                            outputNumber2List.add(Arithmetic.divide(mathValues.get(0), mathValues.get(1), mathMethod.isChecked()));
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException ex) {
                    Log.d("mathValues", Objects.requireNonNull(ex.getLocalizedMessage()));
                }
                break;
            default:
                break;
        }
        outputNumber.setLength(0);
        updateListView(outputListView1, outputNumber1List);
        updateListView(outputListView2, outputNumber2List);

    }

    // region Update Controls

    private void updateActiveWindows(int window) {
        if (window == 1) {
            String currentStateName = jobNumberWindow1Spinner.getSelectedItem().toString();
            outputListView1.setBackgroundColor(getResources().getColor(R.color.activeBackground));
            outputListView2.setBackgroundColor(getResources().getColor(R.color.inactiveBackground));
            OutputState state;
            if (currentStateName.equals(Constants.NEW_SAVE)) {
                state = new OutputState();
                state.setAngle1(0.0);
                state.setAngle2(0.0);
                state.setAngle3(0.0);
                state.setAngle4(0.0);
            } else {
                state = db.outputStateDao().getOutputStateFromName(currentStateName);
            }
            List<Double> newAngles = new ArrayList<>();
            newAngles.add(state.getAngle1());
            newAngles.add(state.getAngle2());
            newAngles.add(state.getAngle3());
            newAngles.add(state.getAngle4());
            state1Angles = DataProvider.updateAngles(state1Angles, newAngles);
            updateRadioButtonValues(state1Angles);
        } else {
            String currentStateName = jobNumberWindow1Spinner.getSelectedItem().toString();
            outputListView2.setBackgroundColor(getResources().getColor(R.color.activeBackground));
            outputListView1.setBackgroundColor(getResources().getColor(R.color.inactiveBackground));
            OutputState state;
            if (currentStateName.equals(Constants.NEW_SAVE)) {
                state = new OutputState();
                state.setAngle1(0.0);
                state.setAngle2(0.0);
                state.setAngle3(0.0);
                state.setAngle4(0.0);
            } else {
                state = db.outputStateDao().getOutputStateFromName(currentStateName);
            }
            List<Double> newAngles = new ArrayList<>();
            newAngles.add(state.getAngle1());
            newAngles.add(state.getAngle2());
            newAngles.add(state.getAngle3());
            newAngles.add(state.getAngle4());
            state2Angles = DataProvider.updateAngles(state2Angles, newAngles);
            updateRadioButtonValues(state2Angles);
        }
    }

    private void updateListView(ListView listView, List<Double> values) {
        Double[] listOfDouble = new Double[values.size()];

        for (int i = 0; i < values.size(); i++) {
            listOfDouble[i] = Converters.round(values.get(i),4);
        }

        ArrayAdapter<Double> adapter = new ArrayAdapter<>(this, R.layout.list_view_layout, R.id.listViewItem, listOfDouble);

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

    private void setRoofSlopeTextOnStartup() {
        String name = jobNumberWindow1Spinner.getAdapter().getItem(0).toString();
        if (name.equals(Constants.NEW_SAVE)) return;

        OutputState state = db.outputStateDao().getOutputStateFromName(name);
        state1Angles = new ArrayList<>();
        state1Angles.add(state.getAngle1());
        state1Angles.add(state.getAngle2());
        state1Angles.add(state.getAngle3());
        state1Angles.add(state.getAngle4());
        switch (activeAngleNumber) {
            case 1:
                updateRoofSlopeText(state1Angles.get(0));
                break;
            case 2:
                updateRoofSlopeText(state1Angles.get(1));
                break;
            case 3:
                updateRoofSlopeText(state1Angles.get(2));
                break;
            case 4:
                updateRoofSlopeText(state1Angles.get(3));
                break;
            default:
                updateRoofSlopeText(0.0);
                break;
        }

    }

    private void updateRoofSlopeText(Double angle) {
        double roofSlope = Trig.getRoofSlopeFromAngle(angle);
        roofSlope *= 12;
        roofSlope = Converters.round(roofSlope, 4);
        currentRoofSlope.setText(String.valueOf(roofSlope));
    }

    // endregion

    // region OnClickListeners

    private void setOnClickListeners() {

        Button[] numberButtons = new Button[]{zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton};
        setNumberButtonOnClickListener(numberButtons);

        setListViewOnClickListeners();
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

    @SuppressLint("ClickableViewAccessibility")
    private void setListViewOnClickListeners() {

        outputListView1.setOnItemClickListener((parent, view, position, id) -> {

        });

        outputListView1.setOnItemLongClickListener((parent, view, position, id) -> {
            //May do something here one day
            return false;
        });

        outputListView1.setOnTouchListener((v, event) -> {
            activeWindow = 1;
            updateActiveWindows(activeWindow);
            return false;
        });

        outputListView2.setOnItemClickListener((parent, view, position, id) -> {

        });

        outputListView2.setOnItemLongClickListener((parent, view, position, id) -> {
            //May do something here one day
            return false;
        });

        outputListView2.setOnTouchListener((v, event) -> {
            activeWindow = 2;
            updateActiveWindows(activeWindow);
            return false;
        });
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

        jobNumberWindow1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activeWindow = 1;
                updateActiveWindows(activeWindow);

                String selected = jobNumberWindow1Spinner.getSelectedItem().toString();

                if (!selected.equals(Constants.NEW_SAVE)) {
                    OutputState state = db.outputStateDao().getOutputStateFromName(selected);
                    outputNumber1List = state.getValues();
                    updateListView(outputListView1, outputNumber1List);
                    List<Double> newAngles = new ArrayList<>();
                    newAngles.add(state.getAngle1());
                    newAngles.add(state.getAngle2());
                    newAngles.add(state.getAngle3());
                    newAngles.add(state.getAngle4());
                    state1Angles = DataProvider.updateAngles(state1Angles, newAngles);
                    updateRadioButtonValues(state1Angles);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        jobNumberWindow2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activeWindow = 2;
                updateActiveWindows(activeWindow);
                String selected = jobNumberWindow2Spinner.getSelectedItem().toString();

                if (!selected.equals(Constants.NEW_SAVE)) {
                    OutputState state = db.outputStateDao().getOutputStateFromName(selected);
                    outputNumber2List = state.getValues();
                    updateListView(outputListView2, outputNumber2List);
                    List<Double> newAngles = new ArrayList<>();
                    newAngles.add(state.getAngle1());
                    newAngles.add(state.getAngle2());
                    newAngles.add(state.getAngle3());
                    newAngles.add(state.getAngle4());
                    state2Angles = DataProvider.updateAngles(state2Angles, newAngles);
                    updateRadioButtonValues(state2Angles);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    private void saveNewState(List<Double> numberList, List<Double> stateAngles) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save New State");

        final EditText input = new EditText(getApplicationContext());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            OutputState newState = new OutputState();
            String name = input.getText().toString();
            newState.setName(name);
            newState.setValues(numberList);
            newState.setAngle1(stateAngles.get(0));
            newState.setAngle2(stateAngles.get(1));
            newState.setAngle3(stateAngles.get(2));
            newState.setAngle4(stateAngles.get(3));
            Calendar c = Calendar.getInstance();
            newState.setCreateDate(c.getTime());
            newState.setModifiedDate(c.getTime());
            db.outputStateDao().insert(newState);
            populateOutputSpinners();

            switch (activeWindow) {
                case 1:
                    for(int i = 0; i < jobNumberWindow1Spinner.getAdapter().getCount(); i++) {
                        String currentName = db.outputStateDao().getOutputStateFromName(name).getName();
                        if(jobNumberWindow1Spinner.getAdapter().getItem(i).toString().contains(currentName)) {
                            jobNumberWindow1Spinner.setSelection(i);
                        }
                    }
                    break;
                case 2:
                    for(int i = 0; i < jobNumberWindow2Spinner.getAdapter().getCount(); i++) {
                        String currentName = db.outputStateDao().getOutputStateFromName(name).getName();
                        if(jobNumberWindow2Spinner.getAdapter().getItem(i).toString().contains(currentName)) {
                            jobNumberWindow2Spinner.setSelection(i);
                        }
                    }
                    break;
                default:
                    break;
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void setOutputSpinnerSaveButtonOnClickListeners() {
        saveState1Button.setOnClickListener(v -> {
            boolean newSave = jobNumberWindow1Spinner.getSelectedItem().toString().equals(Constants.NEW_SAVE);

            if (newSave) {
                saveNewState(outputNumber1List,state1Angles);
                Toast.makeText(getApplicationContext(), jobNumber, Toast.LENGTH_SHORT).show();
            } else {

                OutputState state;
                state = db.outputStateDao().getOutputStateFromName(jobNumberWindow1Spinner.getSelectedItem().toString());
                state.setAngle1(state1Angles.get(0));
                state.setAngle2(state1Angles.get(1));
                state.setAngle3(state1Angles.get(2));
                state.setAngle4(state1Angles.get(3));
                state.setValues(outputNumber1List);
                Calendar c = Calendar.getInstance();
                state.setModifiedDate(c.getTime());
                db.outputStateDao().update(state);
            }
            populateOutputSpinners();
        });

        saveState2Button.setOnClickListener(v -> {
            boolean newSave = jobNumberWindow2Spinner.getSelectedItem().toString().equals(Constants.NEW_SAVE);

            if (newSave) {
                saveNewState(outputNumber2List,state2Angles);
            } else {
                OutputState state;

                state = db.outputStateDao().getOutputStateFromName(jobNumberWindow2Spinner.getSelectedItem().toString());
                state.setAngle1(state2Angles.get(0));
                state.setAngle2(state2Angles.get(1));
                state.setAngle3(state2Angles.get(2));
                state.setAngle4(state2Angles.get(3));
                state.setValues(outputNumber2List);
                Calendar c = Calendar.getInstance();
                state.setModifiedDate(c.getTime());
                db.outputStateDao().update(state);
            }

            populateOutputSpinners();

        });
    }

    private void setArithmeticButtonOnClickListeners() {
        minusButton.setOnClickListener(v -> doMath(ArithmeticFunction.SUBTRACT));
        plusButton.setOnClickListener(v -> doMath(ArithmeticFunction.ADD));
        divideButton.setOnClickListener(v -> doMath(ArithmeticFunction.DIVIDE));
        multiplyButton.setOnClickListener(v -> doMath(ArithmeticFunction.MULTIPLY));
    }

    private void setTrigonometryButtonOnClickListeners() {
        riseToBaseButton.setOnClickListener(v -> {
            try {
                doTrig(TrigFunction.R2B);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Toast.makeText(getApplicationContext(), "You must either have a number in the window or a number entered.", Toast.LENGTH_LONG).show();
                Log.d("NoValues", Objects.requireNonNull(ex.getLocalizedMessage()));
            }
        });

        riseToSlopeButton.setOnClickListener(v -> {
            try {
                doTrig(TrigFunction.R2S);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Toast.makeText(getApplicationContext(), "You must either have a number in the window or a number entered.", Toast.LENGTH_LONG).show();
                Log.d("NoValues", Objects.requireNonNull(ex.getLocalizedMessage()));
            }
        });

        baseToRiseButton.setOnClickListener(v -> {
            try {
                doTrig(TrigFunction.B2R);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Toast.makeText(getApplicationContext(), "You must either have a number in the window or a number entered.", Toast.LENGTH_LONG).show();
                Log.d("NoValues", Objects.requireNonNull(ex.getLocalizedMessage()));
            }
        });

        baseToSlopeButton.setOnClickListener(v -> {
            try {
               doTrig(TrigFunction.B2S);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Toast.makeText(getApplicationContext(), "You must either have a number in the window or a number entered.", Toast.LENGTH_LONG).show();
                Log.d("NoValues", Objects.requireNonNull(ex.getLocalizedMessage()));
            }
        });

        slopeToBaseButton.setOnClickListener(v -> {
            try {
                doTrig(TrigFunction.S2B);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Toast.makeText(getApplicationContext(), "You must either have a number in the window or a number entered.", Toast.LENGTH_LONG).show();
                Log.d("NoValues", Objects.requireNonNull(ex.getLocalizedMessage()));
            }
        });

        slopeToRiseButton.setOnClickListener(v -> {
            try {
                doTrig(TrigFunction.S2R);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Toast.makeText(getApplicationContext(), "You must either have a number in the window or a number entered.", Toast.LENGTH_LONG).show();
                Log.d("NoValues", Objects.requireNonNull(ex.getLocalizedMessage()));
            }
        });
    }

    private Bundle getBundleForActiveWindow(int window) {
        Bundle extras = new Bundle();
        if(window == 1) {
            extras.putDoubleArray(Constants.ANGLES_BUNDLE, Converters.listOfDoubleToDoubleArray(state1Angles));
            String jobNumber = jobNumberWindow1Spinner.getSelectedItem().toString();
            extras.putString(Constants.JOB_NUMBER_BUNDLE, jobNumber);
        } else {
            extras.putDoubleArray(Constants.ANGLES_BUNDLE, Converters.listOfDoubleToDoubleArray(state2Angles));
            String jobNumber = jobNumberWindow2Spinner.getSelectedItem().toString();
            extras.putString(Constants.JOB_NUMBER_BUNDLE, jobNumber);
        }
        RadioButton[] radioButtons = {angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton};
        for(int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked()) {
                extras.putInt(Constants.SELECTED_ANGLE_BUNDLE, i);
                return extras;
            }
        }
        return extras;
    }

    private void setActivityButtonOnClickListeners() {
        calculateWeightButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WeightCalculatorActivity.class);
            intent.putExtra(Constants.ACTIVE_WINDOW, activeWindow);
            startActivity(intent);
        });

        anglesButton.setOnClickListener(v -> {

            Bundle extras = getBundleForActiveWindow(activeWindow);

            Intent intent = new Intent(getApplicationContext(), AngleCalculatorActivity.class);

            intent.putExtras(extras);
            startActivity(intent);

        });
        editJobsButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), EditJobsActivity.class);
            startActivity(intent);
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
            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
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
            switch (activeWindow) {
                case 1:
                    updateRoofSlopeText(state1Angles.get(0));
                    break;
                case 2:
                    updateRoofSlopeText(state2Angles.get(0));
                    break;
                default:
                    break;
            }
            activeAngleNumber = 1;
        });
        angle2RadioButton.setOnClickListener(v -> {
            switch (activeWindow) {
                case 1:
                    updateRoofSlopeText(state1Angles.get(1));
                    break;
                case 2:
                    updateRoofSlopeText(state2Angles.get(1));
                    break;
                default:
                    break;
            }
            activeAngleNumber = 2;
        });
        angle3RadioButton.setOnClickListener(v -> {
            switch (activeWindow) {
                case 1:
                    updateRoofSlopeText(state1Angles.get(2));
                    break;
                case 2:
                    updateRoofSlopeText(state2Angles.get(2));
                    break;
                default:
                    break;
            }
            activeAngleNumber = 3;
        });
        angle4RadioButton.setOnClickListener(v -> {
            switch (activeWindow) {
                case 1:
                    updateRoofSlopeText(state1Angles.get(3));
                    break;
                case 2:
                    updateRoofSlopeText(state2Angles.get(3));
                    break;
                default:
                    break;
            }
            activeAngleNumber = 4;
        });

    }

    private void setSwitchOnClickListeners() {
        mathMethod.setOnCheckedChangeListener((buttonView, isChecked) -> mathMethod.setText(mathMethod.isChecked() ? "Detailing" : "Standard"));
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

            stateArray[states.size()] = Constants.NEW_SAVE;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, R.layout.spinner_view_layout, R.id.spinnerViewItem, stateArray
            );
            adapter.setDropDownViewResource(R.layout.spinner_view_layout);
            jobNumberWindow1Spinner.setAdapter(adapter);
            jobNumberWindow2Spinner.setAdapter(adapter);
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

        // Spinners
        jobNumberWindow1Spinner = findViewById(R.id.jobNumberWindow1Spinner);
        jobNumberWindow2Spinner = findViewById(R.id.jobNumberWindow2Spinner);

        // List Views
        outputListView1 = findViewById(R.id.output1ListView);
        outputListView2 = findViewById(R.id.output2ListView);

        // State Buttons
        saveState1Button = findViewById(R.id.saveState1Button);
        saveState2Button = findViewById(R.id.saveState2Button);
        editJobsButton = findViewById(R.id.editJobNumbersButton);

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
        slopeToBaseButton = findViewById(R.id.slopeToBaseButton);
        slopeToRiseButton = findViewById(R.id.slopeToRiseButton);

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
