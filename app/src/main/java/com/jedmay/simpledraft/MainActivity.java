package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.helper.AlertHelper;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.SampleDbData;
import com.jedmay.simpledraft.model.OutputState;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Spinner output1Spinner, output2Spinner;
    ListView outputListView1, outputListView2;
    Button calculateWeightButton, setSlopeButton, enterAngleButton, deleteButton, clearButton,
    footToDecimalButton, decimalToFootButton,
    riseToSlopeButton, riseToBaseButton, baseToSlopeButton, baseToRiseButton, slopeToBaseButton, slopeToRiseButton;
    Button divideButton, multiplyButton, minusButton, plusButton, enterButton, decimalButton;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;

    Switch mathMethod, outputWindowSwitch;

    SimpleDraftDbBadCompany db;
    SampleDbData sampleDbData;

    StringBuilder outputNumber;

    List<Double> outputNumber1List, outputNumber2List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());
        sampleDbData = new SampleDbData(getApplicationContext());
        sampleDbData.populateDbWithSampleData();

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
        setClearScreenButtonOnClickListeners();

    }

    private void setDecimalButtonOnClickListener() {
        decimalButton.setOnClickListener(v -> {
            if(outputNumber == null) {
                outputNumber = new StringBuilder();
                outputNumber.append("0.");
            } else if(outputNumber.toString().contains(".")) {
                return;
            } else {
                outputNumber.append(".");
            } Toast.makeText(getApplicationContext(),outputNumber.toString(),Toast.LENGTH_SHORT).show();
        });
    }

    private void setClearScreenButtonOnClickListeners() {
        deleteButton.setOnClickListener(v -> {

        });

        clearButton.setOnClickListener(v -> {

        });
    }

    private void setFragmentButtonOnClickListeners() {
        calculateWeightButton.setOnClickListener(v -> {

        });

        setSlopeButton.setOnClickListener(v -> {

        });

        enterAngleButton.setOnClickListener(v -> {

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
                    //TODO - get values currently in the output window and save them to the db
                } else {
                    outputNumber1List = db.outputStateDao().getOutputStateFromName(selected).getValues();
                    updateListView(outputListView1,outputNumber1List);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        output1Spinner.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertHelper.deleteAlert(db.outputStateDao().getOutputStateFromName(output1Spinner.getSelectedItem().toString()), db, this);
            return false;
        });

        output2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = output2Spinner.getSelectedItem().toString();

                if(selected.equals(Constants.newSave)){
                    //TODO - get values currently in the output window and save them to the db
                } else {
                    outputNumber2List = db.outputStateDao().getOutputStateFromName(selected).getValues();
                    updateListView(outputListView2,outputNumber2List);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        output2Spinner.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertHelper.deleteAlert(db.outputStateDao().getOutputStateFromName(output2Spinner.getSelectedItem().toString()), db, this);
            return false;
        });
    }

    private void updateListView(ListView listView, List<Double> values) {
        String[] listStringArray = new String[values.size()];

        for(int i = 0; i < values.size(); i++) {
            listStringArray[i] = values.get(i).toString();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listStringArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setGravity(Gravity.RIGHT|Gravity.END|Gravity.CENTER_VERTICAL);
                return tv;
            }
        };
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
                Toast.makeText(getApplicationContext(),outputNumber.toString(),Toast.LENGTH_SHORT).show();
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
                    this, android.R.layout.simple_spinner_item, stateArray
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    }

}
