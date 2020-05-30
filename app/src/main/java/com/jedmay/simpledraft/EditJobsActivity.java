package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.model.OutputState;

import java.util.ArrayList;
import java.util.List;

public class EditJobsActivity extends AppCompatActivity {

    Button cancelButton, deleteButton, deleteAllButton;
    ListView jobsListListView;
    List<OutputState> jobsToDelete;

    TextView jobsToDeleteTextView;

    SimpleDraftDbBadCompany db;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jobs);

        cancelButton = findViewById(R.id.cancelJobsButton);
        deleteButton = findViewById(R.id.deleteJobsButton);
        deleteAllButton = findViewById(R.id.deleteAllButton);
        jobsListListView = findViewById(R.id.jobsListListView);
        jobsToDeleteTextView = findViewById(R.id.jobsToDeleteTextView);
        jobsToDelete = new ArrayList<>();
        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());

        updateListView();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        cancelButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Go Back To Calculator?");
            builder.setMessage("Do you want to abandon changes and go back to the calculator?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton("No",((dialog, which) -> dialog.cancel()));
            builder.show();
        });

        deleteButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete the following jobs?");
            StringBuilder messageText = new StringBuilder();
            messageText.append("Are you sure you want to delete the following jobs: \n");
            for (OutputState state : jobsToDelete) {
                messageText.append(state.getName());
                messageText.append("\n");
            }
            builder.setMessage(messageText.toString());
            builder.setPositiveButton("Yes", (dialog, which) -> {
                for(OutputState state : jobsToDelete){
                    db.outputStateDao().delete(state);
                }
                updateListView();
                jobsToDelete.clear();
                updateJobsToDeleteTextView();
            });
            builder.setNegativeButton("No",((dialog, which) -> dialog.cancel()));
            builder.show();
        });

        deleteAllButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nuke The World?");
            builder.setMessage("Are you sure you want to delete all jobs? There's no turning back if you do this.");
            builder.setPositiveButton("Wipe Them Out", (dialog, which) -> db.outputStateDao().deleteAll());
            builder.setNegativeButton("Don't do it.", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        jobsListListView.setOnItemClickListener((parent, view, position, id) -> {
            String stateName = jobsListListView.getAdapter().getItem(position).toString();
            OutputState state = db.outputStateDao().getOutputStateFromName(stateName);
            for (int i = 0; i < jobsToDelete.size(); i++) {
                if (jobsToDelete.get(i).getName().equals(stateName)) {
                    jobsToDelete.remove(i);
                    updateJobsToDeleteTextView();
                    return;
                }
            }
            jobsToDelete.add(state);
            updateJobsToDeleteTextView();
        });

    }

    private void updateJobsToDeleteTextView() {
        StringBuilder jobsToDeleteStringBuilder = new StringBuilder();
        jobsToDeleteStringBuilder.append(getString(R.string.jobs_to_delete));
        jobsToDeleteStringBuilder.append(" ");
        if (jobsToDelete.size() > 1) {
            for (int i = 0; i < jobsToDelete.size() - 1; i++) {
                jobsToDeleteStringBuilder.append(jobsToDelete.get(i).getName());
                jobsToDeleteStringBuilder.append(", ");
            }
            jobsToDeleteStringBuilder.append(jobsToDelete.get(jobsToDelete.size() - 1).getName());
        } else if (jobsToDelete.size() == 1){
            jobsToDeleteStringBuilder.append(jobsToDelete.get(0).getName());
        }
        jobsToDeleteTextView.setText(jobsToDeleteStringBuilder.toString());
    }

    private void updateListView() {
        List<OutputState> states = db.outputStateDao().getAllOutputStates();

        String[] jobNamesArray = new String[states.size()];

        for (int i = 0; i < states.size(); i++) {
            jobNamesArray[i] = states.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.jobs_view_layout,R.id.jobsViewItem,jobNamesArray);
        jobsListListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
