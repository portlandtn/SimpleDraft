package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jedmay.simpledraft.adapters.OutputListAdapter;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.viewModel.OutputStateViewModel;
import com.jedmay.simpledraft.viewModel.UserSettingsViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private OutputStateViewModel mOutputStateViewModel1;
    private OutputStateViewModel mOutputStateViewModel2;
    private UserSettingsViewModel mUserSettingsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOutputStateViewModel1 = new ViewModelProvider(this).get(OutputStateViewModel.class);
        mOutputStateViewModel2 = new ViewModelProvider(this).get(OutputStateViewModel.class);
        mUserSettingsViewModel = new ViewModelProvider(this).get(UserSettingsViewModel.class);

        mOutputStateViewModel1.getAllOutputStates().observe(this, new Observer<List<OutputState>>() {
            OutputListAdapter adapter;
            @Override
            public void onChanged(List<OutputState> outputStates) {
                adapter = populateRecyclerView(findViewById(R.id.outputView1), 1);
                assert adapter != null;
                adapter.setOutputStates(outputStates);
            }
        });

        mOutputStateViewModel2.getAllOutputStates().observe(this, new Observer<List<OutputState>>() {
            OutputListAdapter adapter;
            @Override
            public void onChanged(List<OutputState> outputStates) {
                adapter = populateRecyclerView(findViewById(R.id.outputView2), 2);
                assert adapter != null;
                adapter.setOutputStates(outputStates);
            }
        });

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
