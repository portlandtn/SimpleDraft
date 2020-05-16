package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
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

    Switch mathMethod;

    SimpleDraftDbBadCompany db;
    SampleDbData sampleDbData;

    StringBuilder outputNumber;

    List<Double> outputNumber1View, outputNumber2View;

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

        setOutputSpinnerOnClickListeners(output1Spinner);
        setOutputSpinnerOnClickListeners(output2Spinner);

        Button[] arithmeticButtons = new Button[]{minusButton,plusButton,divideButton,multiplyButton};
        Button[] trigonometryButtons = new Button[]{riseToBaseButton,riseToSlopeButton,baseToRiseButton,baseToSlopeButton,slopeToRiseButton,slopeToBaseButton};
        Button[] fragmentButtons = new Button[]{calculateWeightButton,setSlopeButton,enterAngleButton};
        Button[] clearScreenButtons = new Button[]{deleteButton,clearButton};

    }

    private void setOutputSpinnerOnClickListeners(Spinner outputSpinner) {
        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = outputSpinner.getSelectedItem().toString();

                if(selected.equals(Constants.newSave)){
                    //TODO - get values currently in the output window and save them to the db
                } else {
                    outputNumber1View = db.outputStateDao().getOutputStateFromName(selected).getValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        outputSpinner.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteAlert(db.outputStateDao().getOutputStateFromName(outputSpinner.getSelectedItem().toString()));
            return false;
        });
    }

    private void deleteAlert(OutputState state) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to delete this entry?");
        alertDialogBuilder.setPositiveButton("Delete",
                (arg0, arg1) -> db.outputStateDao().delete(state));
        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
            //do nothing
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void setNumberButtonOnClickListener(Button[] numberButtons) {

        for(int i = 0; i < numberButtons.length; i++){
            final int finalI = i;
            numberButtons[i].setOnClickListener(v -> {
                if (outputNumber == null) {
                    outputNumber = new StringBuilder();
                }
                outputNumber.append(finalI);
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

        // Math method switch
        mathMethod = findViewById(R.id.mathMethodSwitch);

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
