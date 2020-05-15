package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jedmay.simpledraft.adapters.OutputListAdapter;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.viewModel.ViewMod;
import com.jedmay.simpledraft.viewModel.outputFact;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewMod mOutputStateViewModel1;
    private ViewMod mOutputStateViewModel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String stateName1 = "K12J0208";
        String stateName2 = "K12J0209";

        mOutputStateViewModel1 = new ViewModelProvider(this, new outputFact(this.getApplication(), stateName1)).get(ViewMod.class);
        mOutputStateViewModel2 = new ViewModelProvider(this, new outputFact(this.getApplication(), stateName2)).get(ViewMod.class);

//        mOutputStateViewModel1 = new ViewModelProvider(this).get(OutputStateViewModel.class);
//        mOutputStateViewModel2 = new ViewModelProvider(this).get(OutputStateViewModel.class);

        mOutputStateViewModel1.getOutputStateValues().observe(this, new Observer<List<OutputState>>() {
            OutputListAdapter adapter;
            @Override
            public void onChanged(List<OutputState> outputStates) {
                adapter = populateRecyclerView(findViewById(R.id.outputView1), 1);
                assert adapter != null;
                adapter.setOutputStates(outputStates);
            }
        });

        mOutputStateViewModel2.getOutputStateValues().observe(this, new Observer<List<OutputState>>() {
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
