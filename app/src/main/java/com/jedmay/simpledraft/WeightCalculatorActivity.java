package com.jedmay.simpledraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.jedmay.simpledraft.helper.Arithmetic;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.Converters;

import java.util.ArrayList;
import java.util.List;

public class WeightCalculatorActivity extends AppCompatActivity {

    Switch lengthSwitch, widthSwitch, thicknessSwitch;
    EditText lengthEditText, widthEditText, thicknessEditText;
    TextView weightTextView, weightTotalTextView;
    ListView weightListView;
    Button cancelButton, pushButton, clearAllWeightsButton;
    List<Double> weightListValues;
    int activeWindow;
    double currentWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculator);

        weightListValues = new ArrayList<>();

        Intent intent = getIntent();
        activeWindow = intent.getIntExtra(Constants.ACTIVE_WINDOW, 1);
        currentWeight = 0;
        findAllViews();
        updateWeightListView();
        setTextWatchers();
        setSwitchListeners();
        setButtonListeners();
        setWeightListViewListeners();
    }

    private void setTextWatchers() {
        lengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCurrentWeight();
            }
        });
        widthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCurrentWeight();
            }
        });
        thicknessEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCurrentWeight();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void updateCurrentWeight() {
        double length, width, thickness;
        try {
            length = Double.parseDouble(lengthEditText.getText().toString());
            width = Double.parseDouble(widthEditText.getText().toString());
            thickness = Double.parseDouble(thicknessEditText.getText().toString());
        } catch (NumberFormatException ex) {
            return;
        }
        double response = 0;
        if(!lengthEditText.getText().toString().isEmpty() &&
                !widthEditText.getText().toString().isEmpty() &&
                !thicknessEditText.getText().toString().isEmpty()
        ) {
            if (lengthSwitch.isChecked()) {
                length = Converters.footDimensionToDecimalDimension(Double.parseDouble(lengthEditText.getText().toString()));
            }
            if (widthSwitch.isChecked()) {
                width = Converters.footDimensionToDecimalDimension(Double.parseDouble(widthEditText.getText().toString()));
            }
            if (thicknessSwitch.isChecked()) {
                thickness = Converters.footDimensionToDecimalDimension(Double.parseDouble(thicknessEditText.getText().toString()));
            }
            response = Arithmetic.calculateWeightUsingInchDimensions(length * 12, width * 12, thickness * 12);
        }
        weightTextView.setText(String.format("%.4f",response));
        currentWeight = response;
    }

    @SuppressLint("DefaultLocale")
    private void updateWeightTotalTextView() {
        double totalWeight = 0;
        for(Double value : weightListValues){
            totalWeight += value;
        }
        weightTotalTextView.setText(String.format("%.4f",totalWeight));
    }

    private void setSwitchListeners() {
        lengthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lengthSwitch.setText(lengthSwitch.isChecked() ? "Detailing" : "Standard");
            updateCurrentWeight();
        });

        widthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            widthSwitch.setText(widthSwitch.isChecked() ? "Detailing" : "Standard");
            updateCurrentWeight();
        });

        thicknessSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            thicknessSwitch.setText(thicknessSwitch.isChecked() ? "Detailing" : "Standard");
            updateCurrentWeight();
        });
    }

    private void setButtonListeners() {
        pushButton.setOnClickListener(v-> {
            weightListValues.add(currentWeight);
            updateWeightTotalTextView();
            updateWeightListView();
            resetValuesForNewEntry();
        });
        cancelButton.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cancel and return to Calculator?");
            builder.setMessage("Are you sure you want to leave the weight calculator screen and return to the calculator?");
            builder.setPositiveButton("Yes",((dialog, which) -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Constants.ACTIVE_WINDOW, activeWindow);
                startActivity(intent);
                finish();
            }));
            builder.setNegativeButton("No",((dialog, which) -> {
                dialog.cancel();
            }));
            builder.show();
        });
        clearAllWeightsButton.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear all weights?");
            builder.setMessage("Are you sure you want to clear the weights and start over?");
            builder.setPositiveButton("Yes",((dialog, which) -> {
                clearAllValues();
            }));
            builder.setNegativeButton("No",((dialog, which) -> {
                dialog.cancel();
            }));
            builder.show();
        });
    }

    private void setWeightListViewListeners() {
        weightListView.setOnItemClickListener((parent, view, position, id) -> {

            Double value = (Double) weightListView.getAdapter().getItem(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(WeightCalculatorActivity.this);
            builder.setTitle("Delete This Weight?");
            builder.setMessage("Are you sure you want to delete " + value + "?");
            builder.setPositiveButton("Yes",((dialog, which) -> {
                for(int i = 0; i < weightListValues.size(); i++) {
                    if (weightListValues.get(i).equals(value)) {
                        weightListValues.remove(i);
                        updateWeightListView();
                        updateWeightTotalTextView();
                        return;
                    }
                }
            }));
            builder.setNegativeButton("No",((dialog, which) -> {
                dialog.cancel();
            }));
            builder.show();
        });
    }

    private void resetValuesForNewEntry() {
        lengthEditText.setText("");
        widthEditText.setText("");
        thicknessEditText.setText("");
        weightTextView.setText(R.string._0_00);
    }

    private void clearAllValues() {
        lengthEditText.setText("");
        widthEditText.setText("");
        thicknessEditText.setText("");
        weightTextView.setText(R.string._0_00);
        weightTotalTextView.setText(R.string._0_00);
        weightListValues.clear();
        updateWeightListView();
        currentWeight = 0;
    }

    private void updateWeightListView() {
        Double[] weightArray = new Double[weightListValues.size()];
        for (int i = 0; i < weightListValues.size(); i++){
            weightArray[i] = weightListValues.get(i);
        }

        ArrayAdapter<Double> adapter = new ArrayAdapter<>(this,R.layout.jobs_view_layout,R.id.jobsViewItem,weightArray);
        weightListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findAllViews() {
        lengthSwitch = findViewById(R.id.lengthMathMethod);
        widthSwitch = findViewById(R.id.widthMathMethod);
        thicknessSwitch = findViewById(R.id.thicknessMathMethod);
        lengthEditText = findViewById(R.id.lengthEditText);
        widthEditText = findViewById(R.id.widthEditText);
        thicknessEditText = findViewById(R.id.thicknessEditText);
        weightTextView = findViewById(R.id.weightTextView);
        weightTotalTextView = findViewById(R.id.weightTotalTextView);
        weightListView = findViewById(R.id.weightListView);
        cancelButton = findViewById(R.id.cancelWeightButton);
        pushButton = findViewById(R.id.pushToWeightListViewButton);
        clearAllWeightsButton = findViewById(R.id.clearAllWeightsButton);
    }


}
