package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.jedmay.simpledraft.adapters.OutputListAdapter;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.viewModel.OutputStateViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Spinner output1Spinner, output2Spinner;
    OutputStateViewModel mOutputStateViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output1Spinner = findViewById(R.id.output1NameSpinner);
        output2Spinner = findViewById(R.id.output2NameSpinner);
        populateSpinner(output1Spinner);
        populateSpinner(output2Spinner);


    }

    private void populateSpinner(Spinner outputSpinner) {

        List<OutputState> states = mOutputStateViewModel.getAllOutputStates();
        String[] stateArray = new String[states.size()];

        String newSave = "<New Save>";
        stateArray[0] = newSave;

        for(int i = 0; i < states.size() + 1; i++) {
            stateArray[i+1] = states.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stateArray
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputSpinner.setAdapter(adapter);

    }

    private OutputListAdapter populateRecyclerView(View view, int outputViewNumber) {
        try {
            RecyclerView outputView = findViewById(view.getId());
            OutputListAdapter adapter = new OutputListAdapter(this, outputViewNumber);
            outputView.setAdapter(adapter);
            outputView.setLayoutManager(new LinearLayoutManager(this));
            return adapter;
        } catch (Exception ex) {
            Log.d("populateRecycle", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
        return null;
    }
}
