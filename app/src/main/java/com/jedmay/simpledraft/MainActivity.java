package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

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
import com.jedmay.simpledraft.helper.AlertHelper;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.SampleDbData;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.output.ArithmeticClickHelper;
import com.jedmay.simpledraft.output.DataProvider;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Spinner output1Spinner, output2Spinner;
    ListView outputListView1, outputListView2;
    Button calculateWeightButton, setSlopeButton, enterAngleButton, deleteButton, clearButton, backspaceButton,
    footToDecimalButton, decimalToFootButton,
    riseToSlopeButton, riseToBaseButton, baseToSlopeButton, baseToRiseButton, slopeToBaseButton, slopeToRiseButton;
    Button divideButton, multiplyButton, minusButton, plusButton, enterButton, decimalButton;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;
    TextView text1Input, text2Input;

    Switch mathMethod, outputWindowSwitch;

    SimpleDraftDbBadCompany db;
    SampleDbData sampleDbData;
    DataProvider dataProvider;

    StringBuilder outputNumber;

    List<Double> outputNumber1List, outputNumber2List;

    int activeWindow;

    boolean isDetailingMathMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());
        sampleDbData = new SampleDbData(getApplicationContext());
        sampleDbData.populateDbWithSampleData();
        dataProvider = new DataProvider();

        findAllViews();

        populateOutputSpinners();

        setOnClickListeners();

    }

    private void setOnClickListeners() {

        Button[] numberButtons = new Button[]{zeroButton,oneButton,twoButton,threeButton,fourButton,fiveButton,sixButton,sevenButton,eightButton,nineButton};
        setNumberButtonOnClickListener(numberButtons);
        setDecimalButtonOnClickListener();

        setOutputSpinnerOnClickListeners();

        setArithmeticButtonOnClickListeners();
        setTrigonometryButtonOnClickListeners();
        setFragmentButtonOnClickListeners();
        setGeneralButtonOnClickListeners();

        setSwitchOnClickListeners();

    }

    private void setSwitchOnClickListeners() {
        mathMethod.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDetailingMathMethod = mathMethod.isChecked();
            mathMethod.setText(mathMethod.isChecked() ? "Detailing Math" : "Standard Math");
        });

        outputWindowSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activeWindow = outputWindowSwitch.isChecked() ? 2 : 1;
            outputWindowSwitch.setText(outputWindowSwitch.isChecked() ? "Output Window 2" : "Output Window 1");
        });
    }

    private void setDecimalButtonOnClickListener() {
        decimalButton.setOnClickListener(v -> {
            if(outputNumber == null) {
                outputNumber = new StringBuilder();
                outputNumber.append("0.");
            } else if(outputNumber.toString().contains(".")) {
                // do nothing, the decimal is already there, so it should be ignored
            } else {
                outputNumber.append(".");
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
                case 2:
                    if (outputNumber2List.size() > 0) {
                        outputNumber2List.remove(outputNumber2List.size() - 1);
                        updateListView(outputListView2, outputNumber2List);
                    }
                default:
                    break;
            }
        });

        clearButton.setOnClickListener(v -> {
            if(AlertHelper.deleteScreen(getApplicationContext()))
            {
                switch (activeWindow) {
                    case 1:
                        outputNumber1List.clear();
                        updateListView(outputListView1,outputNumber1List);
                    case 2:
                        outputNumber2List.clear();
                        updateListView(outputListView1,outputNumber2List);
                    default:
                        break;
                }
            }
        });

        backspaceButton.setOnClickListener(v -> {
            if (outputNumber.length() > 0) {
                outputNumber.delete(0,outputNumber.length() - 1);
            }
        });

        enterButton.setOnClickListener(v -> {

            switch (activeWindow) {
                case 1:
                    outputNumber1List = dataProvider.getValueFromEnterKeyPress(outputNumber.toString(),outputNumber1List);
                    updateListView(outputListView1, outputNumber1List);
                    break;
                case 2:
                    outputNumber2List = dataProvider.getValueFromEnterKeyPress(outputNumber.toString(),outputNumber2List);
                    updateListView(outputListView2,outputNumber2List);
                    break;
                default:
                    break;
            }
        });
    }


    private void setFragmentButtonOnClickListeners() {
        calculateWeightButton.setOnClickListener(v -> {
            //TODO Must Develop fragment to display weight input
        });

        setSlopeButton.setOnClickListener(v -> {
            //TODO Must develop fragment to set slope, base on #:12
        });

        enterAngleButton.setOnClickListener(v -> {
            //TODO Must develop fragment to let user enter angle number
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
            switch (activeWindow) {
                case 1:
                    outputNumber1List.add(ArithmeticClickHelper.minusButtonClick(outputNumber1List,outputNumber.toString(),isDetailingMathMethod));
                    outputNumber = null;
                    break;
                case 2:
                    outputNumber2List.add(ArithmeticClickHelper.minusButtonClick(outputNumber2List,outputNumber.toString(),isDetailingMathMethod));
                    outputNumber = null;
                    break;
                default:
                    break;
            }
        });
        plusButton.setOnClickListener(v -> {

        });
        divideButton.setOnClickListener(v -> {

        });
        multiplyButton.setOnClickListener(v -> {

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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        output1Spinner.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertHelper.deleteOutputState(db.outputStateDao().getOutputStateFromName(output1Spinner.getSelectedItem().toString()), db, this);
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        output2Spinner.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertHelper.deleteOutputState(db.outputStateDao().getOutputStateFromName(output2Spinner.getSelectedItem().toString()), db, this);
            return false;
        });
    }

    private void updateListView(ListView listView, List<Double> values) {
        String[] listStringArray = new String[values.size()];

        for(int i = 0; i < values.size(); i++) {
            listStringArray[i] = values.get(i).toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view_layout, R.id.listViewItem, listStringArray);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setNumberButtonOnClickListener(Button[] numberButtons) {

        for(int i = 0; i < numberButtons.length; i++){
            final int finalI = i;
            numberButtons[i].setOnClickListener(v -> {
                if (outputNumber == null) {
                    outputNumber = new StringBuilder();
                }
                outputNumber.append(finalI);
                text1Input.setText(outputNumber);
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
                    this, R.layout.list_view_layout, R.id.listViewItem, stateArray
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
        setSlopeButton = findViewById(R.id.setRoofSlopeButton);
        enterAngleButton = findViewById(R.id.setCustomAngleButton);
        deleteButton = findViewById(R.id.deleteButton);
        clearButton = findViewById(R.id.clearButton);
        backspaceButton = findViewById(R.id.backspaceButton);

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

        text1Input = findViewById(R.id.window1Text);
        text2Input = findViewById(R.id.window2Text);

    }

}
