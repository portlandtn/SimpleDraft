package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.helper.SampleDbData;
import com.jedmay.simpledraft.model.OutputState;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Spinner output1Spinner, output2Spinner;

    SimpleDraftDbBadCompany db;
    SampleDbData sampleDbData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());
        sampleDbData = new SampleDbData(getApplicationContext());
        sampleDbData.populateDbWithSampleData();

        findAllViews();

        populateOutputSpinners();
//        populateSpinner(output2Spinner);

    }

    private void findAllViews() {
        angle1RadioButton = findViewById(R.id.angle1RadioButton);
        angle2RadioButton = findViewById(R.id.angle2RadioButton);
        angle3RadioButton = findViewById(R.id.angle3RadioButton);
        angle4RadioButton = findViewById(R.id.angle4RadioButton);

        output1Spinner = findViewById(R.id.output1NameSpinner);
        output2Spinner = findViewById(R.id.output2NameSpinner);
    }

    private void populateOutputSpinners() {
        try {
            List<OutputState> states = db.outputStateDao().getAllOutputStates();
            String[] stateArray = new String[states.size() + 1];

                for(int i = 0; i < states.size(); i++) {
                    stateArray[i] = states.get(i).getName();
                    Toast.makeText(getApplicationContext(),states.get(i).getName(),Toast.LENGTH_LONG).show();
                }

            String newSave = "<New Save>";
            stateArray[states.size()] = newSave;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, stateArray
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            output1Spinner.setAdapter(adapter);
            output2Spinner.setAdapter(adapter);
        } catch (Exception ex) {
            Log.d("pop_Spinner", ex.getLocalizedMessage());
        }
    }

}
