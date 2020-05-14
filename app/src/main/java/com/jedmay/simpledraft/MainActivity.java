package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.jedmay.simpledraft.adapters.OutputListAdapter;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.viewModel.OutputStateViewModel;
import com.jedmay.simpledraft.viewModel.UserSettingsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OutputStateViewModel mOutputStateViewModel1;
    private OutputStateViewModel mOutputStateViewModel2;
    private UserSettingsViewModel mUserSettingsViewModel;

    public static final int NEW_ACTIVITY_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateRecyclerView(R.id.outputView2);

        mOutputStateViewModel1 = new ViewModelProvider(this).get(OutputStateViewModel.class);
        mOutputStateViewModel2 = new ViewModelProvider(this).get(OutputStateViewModel.class);
        mUserSettingsViewModel = new ViewModelProvider(this).get(UserSettingsViewModel.class);

        mOutputStateViewModel1.getAllOutputStates().observe(this, new Observer<List<OutputState>>() {
            OutputListAdapter adapter;
            @Override
            public void onChanged(List<OutputState> outputStates) {
                adapter = populateRecyclerView(R.id.outputView1);
                adapter.setOutputStates(outputStates);
            }
        });

        mOutputStateViewModel2.getAllOutputStates().observe(this, new Observer<List<OutputState>>() {
            OutputListAdapter adapter;
            @Override
            public void onChanged(List<OutputState> outputStates) {
                adapter = populateRecyclerView(R.id.outputView2);
                adapter.setOutputStates(outputStates);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            OutputState outputState = new OutputState(data.getStringExtra(NewOutputStateActivity.EXTRA_REPLY));
            mOutputStateViewModel1.insert(outputState);
        }
    }

    private OutputListAdapter populateRecyclerView(int viewId) {
        RecyclerView outputView = findViewById(viewId);
        final OutputListAdapter adapter = new OutputListAdapter(this, viewId);
        outputView.setAdapter(adapter);
        outputView.setLayoutManager(new LinearLayoutManager(this));
        return adapter;
    }
}
