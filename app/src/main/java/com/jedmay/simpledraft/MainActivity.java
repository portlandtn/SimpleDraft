package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jedmay.simpledraft.adapters.OutputListAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateRecyclerView(R.id.outputView1);
        populateRecyclerView(R.id.outputView2);
    }

    private void populateRecyclerView(int viewId) {
        RecyclerView outputView = findViewById(viewId);
        final OutputListAdapter adapter = new OutputListAdapter(this, viewId);
        outputView.setAdapter(adapter);
        outputView.setLayoutManager(new LinearLayoutManager(this));
    }
}
