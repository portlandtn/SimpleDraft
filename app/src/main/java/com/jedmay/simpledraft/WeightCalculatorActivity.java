package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.Converters;

public class WeightCalculatorActivity extends AppCompatActivity {

    Switch lengthSwitch, widthSwitch, thicknessSwitch;
    EditText lengthEditText, widthEditText, thicknessEditText;
    TextView weightTextView;
    Button cancelButton, pushButton;
    int activeWindow;
    boolean lengthUsesDetailingMethod, widthUsesDetailingMethod, thicknessUsesDetailingMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculator);

        Intent intent = getIntent();
        activeWindow = intent.getIntExtra(Constants.activeWindow, 1);
        findAllViews();
        setTextWatchers();
        setSwitchListeners();

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
//                calculateWeight();
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

            }
        });
    }

    private double calculateWeight(double length, double width, double thickness) {
        double response = 0;
        if(!lengthEditText.getText().toString().isEmpty() &&
                !widthEditText.getText().toString().isEmpty() &&
                !thicknessEditText.getText().toString().isEmpty()
        ) {
            if (lengthUsesDetailingMethod) {
                length = Converters.footDimensionToDecimalDimension(Double.parseDouble(lengthEditText.getText().toString()));
            }
            if (widthUsesDetailingMethod) {
                width = Converters.footDimensionToDecimalDimension(Double.parseDouble(widthEditText.getText().toString()));
            }
            if (thicknessUsesDetailingMethod) {
                thickness = Converters.footDimensionToDecimalDimension(Double.parseDouble(thicknessEditText.getText().toString()));
            }
            response = (length * 12) * (width * 12) * (thickness * 12);
        }
        return response;
    }

    private void setSwitchListeners() {
        lengthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lengthUsesDetailingMethod = lengthSwitch.isChecked();
            lengthSwitch.setText(lengthSwitch.isChecked() ? "Detailing" : "Standard");
        });

        widthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            widthUsesDetailingMethod = widthSwitch.isChecked();
            widthSwitch.setText(widthSwitch.isChecked() ? "Detailing" : "Standard");
        });

        thicknessSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            thicknessUsesDetailingMethod = thicknessSwitch.isChecked();
            thicknessSwitch.setText(thicknessSwitch.isChecked() ? "Detailing" : "Standard");
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
