package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jedmay.simpledraft.db.SimpleDraftDb;
import com.jedmay.simpledraft.helper.Converters;
import com.jedmay.simpledraft.model.OutputState;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditJobsActivity extends AppCompatActivity {

    Button cancelButton, deleteButton, deleteAllButton;
    ListView jobsListListView;
    List<OutputState> jobsToDelete;
    Spinner timeFrameSpinner;

    TextView jobsToDeleteTextView;

    SimpleDraftDb db;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jobs);

        timeFrameSpinner = findViewById(R.id.timeFrameSpinner);
        cancelButton = findViewById(R.id.cancelJobsButton);
        deleteButton = findViewById(R.id.deleteJobsButton);
        deleteAllButton = findViewById(R.id.deleteAllButton);
        jobsListListView = findViewById(R.id.jobsListListView);
        jobsToDeleteTextView = findViewById(R.id.jobsToDeleteTextView);
        jobsToDelete = new ArrayList<>();
        db = SimpleDraftDb.getDatabase(getApplicationContext());

        setupTimeFrameSpinner();

        updateListView(0);
        setOnClickListeners();
        setOnClickListenersForSpinner();

    }

    private void setupTimeFrameSpinner() {
        String[] timeFrames = getResources().getStringArray(R.array.time_period);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_view_layout, R.id.spinnerViewItem, timeFrames);
        adapter.setDropDownViewResource(R.layout.spinner_view_layout);
        timeFrameSpinner.setAdapter(adapter);
    }

    private void setOnClickListenersForSpinner() {

        timeFrameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String timePeriod = timeFrameSpinner.getAdapter().getItem(position).toString();

                switch (timePeriod) {
                    case "Unchanged in 30 Days Or less":
                        updateListView(1);
                        break;
                    case "Unchanged 30 – 60 days":
                        updateListView(2);
                        break;
                    case "Unchanged more than 90 days":
                        updateListView(3);
                        break;
                    default: // All
                        updateListView(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                updateListView(0);
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
            builder.setPositiveButton("Wipe Them Out", (dialog, which) -> {

                db.outputStateDao().deleteAll();
                updateJobsToDeleteTextView();
                updateListView(0);
            });
            builder.setNegativeButton("Don't do it.", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        jobsListListView.setOnItemClickListener((parent, view, position, id) -> {
            String stateName = jobsListListView.getAdapter().getItem(position).toString();// full file name
            int delimiter = stateName.indexOf(" | "); //this finds the first occurrence of "."
            String subString = "";
            if (delimiter != -1)
            {
                subString= stateName.substring(0 , delimiter); //this will give abc
            }


            if (!subString.equals("") ) {
                OutputState state = db.outputStateDao().getOutputStateFromName(subString);
                for (int i = 0; i < jobsToDelete.size(); i++) {
                    if (jobsToDelete.get(i).getName().equals(stateName)) {
                        jobsToDelete.remove(i);
                        updateJobsToDeleteTextView();
                        return;
                    }
                }
                jobsToDelete.add(state);
            }
            updateJobsToDeleteTextView();
        });

        jobsListListView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setTitle("Rename job number");
            builder.setMessage("Enter the new job number");
            builder.setPositiveButton("OK",((dialog, which) -> {
                String currentName = jobsListListView.getAdapter().getItem(position).toString();
                OutputState state = db.outputStateDao().getOutputStateFromName(currentName);
                state.setName(input.getText().toString());
                db.outputStateDao().update(state);
            }));
            builder.setNegativeButton("Cancel", ((dialog, which) -> {
                dialog.cancel();
            }));
            return false;
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

    private void updateListView(int timeFrame) {
        List<OutputState> states = null;

        Calendar c = Calendar.getInstance();

        switch (timeFrame) {
            case 1:
                c.add(Calendar.MONTH, -1);
                states = db.outputStateDao().getOutputStateFromDateRange(c.getTimeInMillis());
                break;
            case 2:
                c.add(Calendar.MONTH, -2);
                states = db.outputStateDao().getOutputStateFromDateRange(c.getTimeInMillis());
                break;
            case 3:
                c.add(Calendar.MONTH, -3);
                states = db.outputStateDao().getOutputStateFromDateRange(c.getTimeInMillis());
                break;
            default: // All
                states = db.outputStateDao().getAllOutputStates();
                break;
        }

        String[] jobNamesArray = new String[states.size()];

        for (int i = 0; i < states.size(); i++) {
            String rowOfData = states.get(i).getName() +
                    " | " +
                    Converters.formatDate(states.get(i).getModifiedDate());
            jobNamesArray[i] = rowOfData;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.jobs_view_layout,R.id.jobsViewItem,jobNamesArray);
        jobsListListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
