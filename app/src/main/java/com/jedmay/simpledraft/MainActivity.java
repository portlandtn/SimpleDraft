package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    TextView outputNumberTextView, currentRoofSlope;

    Switch mathMethod, outputWindowSwitch;

    SimpleDraftDbBadCompany db;
    SampleDbData sampleDbData;

    StringBuilder outputNumber;

    List<Double> outputNumber1List, outputNumber2List, state1Angles, state2Angles;

    int activeWindow, activeAngle;

    boolean isDetailingMathMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());
        sampleDbData = new SampleDbData(getApplicationContext());
        sampleDbData.populateDbWithSampleData();
        activeWindow = 1;
        activeAngle = 1;
        isDetailingMathMethod = false;
        outputNumber = new StringBuilder();

        findAllViews();

        populateOutputSpinners();

        setOnClickListeners();

        setSlopeText();

    }

    private void setSlopeText() {
        switch (activeAngle) {
            case 1:
                setRoofSlopeText(angle1RadioButton.getText().toString());
                break;
            case 2:
                setRoofSlopeText(angle2RadioButton.getText().toString());
                break;
            case 3:
                setRoofSlopeText(angle3RadioButton.getText().toString());
                break;
            case 4:
                setRoofSlopeText(angle4RadioButton.getText().toString());
                break;
            default:
                setRoofSlopeText("0");
                break;
        }

    }

    private void setOnClickListeners() {

        Button[] numberButtons = new Button[]{zeroButton,oneButton,twoButton,threeButton,fourButton,fiveButton,sixButton,sevenButton,eightButton,nineButton};
        setNumberButtonOnClickListener(numberButtons);

        setDecimalButtonOnClickListener();
        setOutputSpinnerOnClickListeners();
        setArithmeticButtonOnClickListeners();
        setTrigonometryButtonOnClickListeners();
        setActivityButtonOnClickListeners();
        setGeneralButtonOnClickListeners();
        setRadioButtonOnClickListeners();
        setSwitchOnClickListeners();

    }

    private void setRoofSlopeText(String angleValue) {
        double angle = Double.parseDouble(angleValue);
        double roofSlope = Trig.getRoofSlopeFromAngle(angle);
        currentRoofSlope.setText(String.valueOf(Converters.round(roofSlope, 4)));
    }

    private void setRadioButtonOnClickListeners() {

        angle1RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(angle1RadioButton.getText().toString());
            activeAngle = 1;
        });
        angle2RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(angle2RadioButton.getText().toString());
            activeAngle = 2;
        });
        angle3RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(angle3RadioButton.getText().toString());
            activeAngle = 3;
        });
        angle4RadioButton.setOnClickListener(v -> {
            setRoofSlopeText(angle4RadioButton.getText().toString());
            activeAngle = 4;
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

    private void setDecimalButtonOnClickListener() {
        decimalButton.setOnClickListener(v -> {
            if(outputNumber.length() == 0) {
                outputNumber = new StringBuilder();
                outputNumber.append("0.");
            } else if(outputNumber.toString().contains(".")) {
                // do nothing, the decimal is already there, so button press should be ignored
            } else {
                outputNumber.append(".");
                outputNumberTextView.setText(outputNumber.toString());
            }
        });
    }

    private void setGeneralButtonOnClickListeners() {
        deleteButton.setOnClickListener(v -> {
            switch(activeWindow) {
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
                        updateListView(outputListView1,outputNumber1List);
                        break;
                    case 2:
                        outputNumber2List.clear();
                        updateListView(outputListView2,outputNumber2List);
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
                    outputNumber1List = DataProvider.getValueFromEnterKeyPress(outputNumber.toString(),outputNumber1List);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List = DataProvider.getValueFromEnterKeyPress(outputNumber.toString(),outputNumber2List);
                    updateListView(outputListView2,outputNumber2List);
                    break;
                default:
                    break;
            }
            outputNumber.setLength(0);
            outputNumberTextView.setText(outputNumber.toString());
        });

        negativePositiveButton.setOnClickListener(v -> {
            double changeUp;
            switch(activeWindow) {
                case 1:
                    changeUp = outputNumber1List.get(outputNumber1List.size() - 1);
                    changeUp *= -1;
                    outputNumber1List.set(outputNumber1List.size() -1,changeUp);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    changeUp = outputNumber2List.get(outputNumber2List.size() - 1);
                    changeUp *= -1;
                    outputNumber2List.set(outputNumber2List.size() -1,changeUp);
                    updateListView(outputListView2, outputNumber2List);
                    break;
                default:
                    break;
            }
        });
    }


    private void setActivityButtonOnClickListeners() {
        calculateWeightButton.setOnClickListener(v -> {
            //TODO Must Develop fragment to display weight input
        });

        anglesButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AngleCalculatorActivity.class);
//            i.putExtra("angle 1", )
        });
    }

    private void setTrigonometryButtonOnClickListeners() {
        riseToBaseButton.setOnClickListener(v -> {

        });

        riseToSlopeButton.setOnClickListener(v -> {

        });

        baseToRiseButton.setOnClickListener(v -> {

        });

        baseToSlopeButton.setOnClickListener(v -> {

        });

        slopeToBaseButton.setOnClickListener(v -> {

        });

        slopeToRiseButton.setOnClickListener(v -> {

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

    private void setOutputSpinnerOnClickListeners() {

        output1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = output1Spinner.getSelectedItem().toString();

                if(selected.equals(Constants.newSave)){
                    //TODO - get values currently in the output window and save them to the db - will need a fragment
                } else {
                    outputNumber1List = db.outputStateDao().getOutputStateFromName(selected).getValues();
                    updateListView(outputListView1,outputNumber1List);
                    state1Angles = db.outputStateDao().getAnglesFromStateName(selected);
//                    updateAngleRadioButtons();
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

                if(selected.equals(Constants.newSave)){
                    //TODO - get values currently in the output window and save them to the db - will need a fragment
                } else {
                    outputNumber2List = db.outputStateDao().getOutputStateFromName(selected).getValues();
                    updateListView(outputListView2,outputNumber2List);
                    state2Angles = db.outputStateDao().getAnglesFromStateName(selected);

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

    private void updateListView(ListView listView, List<Double> values) {
        String[] listStringArray = new String[values.size()];

        for(int i = 0; i < values.size(); i++) {
            listStringArray[i] = values.get(i).toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_view_layout, R.id.listViewItem, listStringArray);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setNumberButtonOnClickListener(Button[] numberButtons) {

        for(int i = 0; i < numberButtons.length; i++){
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
            adapter.setDropDownViewResource(R.layout.list_view_layout);
            output1Spinner.setAdapter(adapter);
            output2Spinner.setAdapter(adapter);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("pop_index", Objects.requireNonNull(ex.getLocalizedMessage()));
        } catch (Exception ex) {
            Log.d("pop_general", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
    }

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

        //List Views
        outputListView1 = findViewById(R.id.output1ListView);
        outputListView2 = findViewById(R.id.output2ListView);

        //Buttons
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

}
