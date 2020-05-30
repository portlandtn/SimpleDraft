package com.jedmay.simpledraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.jedmay.simpledraft.helper.Arithmetic;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.Converters;

public class WeightCalculatorActivity extends AppCompatActivity {

    Switch lengthSwitch, widthSwitch, thicknessSwitch;
    EditText lengthEditText, widthEditText, thicknessEditText;
    TextView weightTextView;
    Button cancelButton, pushButton;
    int activeWindow;
    double weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculator);

        Intent intent = getIntent();
        activeWindow = intent.getIntExtra(Constants.activeWindow, 1);
        weight = 0;
        findAllViews();
        setTextWatchers();
        setSwitchListeners();
        setButtonListeners();
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
                updateWeightTextView();
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
                updateWeightTextView();
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
                updateWeightTextView();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void updateWeightTextView() {
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
    }

    private void setSwitchListeners() {
        lengthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lengthSwitch.setText(lengthSwitch.isChecked() ? "Detailing" : "Standard");
            updateWeightTextView();
        });

        widthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            widthSwitch.setText(widthSwitch.isChecked() ? "Detailing" : "Standard");
            updateWeightTextView();
        });

        thicknessSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            thicknessSwitch.setText(thicknessSwitch.isChecked() ? "Detailing" : "Standard");
            updateWeightTextView();
        });
    }

    private void setButtonListeners() {
        pushButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(Constants.weight,weight);
            intent.putExtra(Constants.activeWindow, activeWindow);
            startActivity(intent);
            finish();
        });
        cancelButton.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cancel and return to Calculator?");
            builder.setMessage("Are you sure you want to leave the weight calculator screen and return to the calculator?");
            builder.setPositiveButton("Yes",((dialog, which) -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Constants.activeWindow, activeWindow);
                startActivity(intent);
                finish();
            }));
            builder.setNegativeButton("No",((dialog, which) -> {
                dialog.cancel();
            }));
        });
    }

    private void findAllViews() {
        lengthSwitch = findViewById(R.id.lengthMathMethod);
        widthSwitch = findViewById(R.id.widthMathMethod);
        thicknessSwitch = findViewById(R.id.thicknessMathMethod);
        lengthEditText = findViewById(R.id.lengthEditText);
        widthEditText = findViewById(R.id.widthEditText);
        thicknessEditText = findViewById(R.id.thicknessEditText);
        weightTextView = findViewById(R.id.weightTextView);
        cancelButton = findViewById(R.id.cancelWeightButton);
        pushButton = findViewById(R.id.pushWeightButton);
    }


}
