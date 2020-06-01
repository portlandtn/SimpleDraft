package com.jedmay.simpledraft;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.helper.Constants;
import com.jedmay.simpledraft.helper.Converters;
import com.jedmay.simpledraft.helper.Trig;
import com.jedmay.simpledraft.model.OutputState;

import java.util.List;
import java.util.Objects;

public class AngleCalculatorActivity extends AppCompatActivity {

    SimpleDraftDbBadCompany db;
    Spinner jobNumberSpinner;
    EditText riseEditText, baseEditText, angleEditText;
    RadioButton angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton;
    Button saveButton, cancelButton;
    double[] angles;
    String jobNumber;
    int activeAngle;
    double baseDimension, riseDimension, angleForCalc;
    OutputState state;

    @Override
    public void onBackPressed() {
        setAngles();
        finish();
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angle_calculator);
        db = SimpleDraftDbBadCompany.getDatabase(getApplicationContext());
        findAllViews();
        //Set base to 1.0000 for :12 default
        baseDimension = 1.0;
        baseEditText.setText(String.format("%.4f", baseDimension));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            jobNumber = extras.getString(Constants.jobNumberBundle);
            angles = extras.getDoubleArray(Constants.anglesBundle);
            activeAngle = extras.getInt(Constants.selectedAngleBundle);
        }

        populateSpinner();
        updateEditTextViews();
        setSelectedAngleOnCreate();
        setOnClickListeners();
        setTextWatchersOnEditTextFields();
    }

    private void setSelectedAngleOnCreate() {
        switch (activeAngle) {
            case 0:
                angle1RadioButton.setChecked(true);
                break;
            case 1:
                angle2RadioButton.setChecked(true);
                break;
            case 2:
                angle3RadioButton.setChecked(true);
                break;
            case 3:
                angle4RadioButton.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void setTextWatchersOnEditTextFields() {
        riseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(riseEditText.hasFocus()){
                    if(!riseEditText.getText().toString().isEmpty()) {
                        updateDimensionFromDimensionChange();
                    }
                }
            }
        });

        baseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(baseEditText.hasFocus()) {
                    if (!baseEditText.getText().toString().isEmpty()){
                        updateDimensionFromDimensionChange();
                    }
                }
            }
        });

        angleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(angleEditText.hasFocus()) {
                    if(!angleEditText.getText().toString().isEmpty()) {
                        angleForCalc = Double.parseDouble(angleEditText.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(angleEditText.hasFocus()){
                    if(!angleEditText.getText().toString().isEmpty()) {
                        updateDimensionsOnAngleChange();
                    }
                }
            }
        });
    }

    private void setOnClickListeners() {
        jobNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String jobNumber = jobNumberSpinner.getSelectedItem().toString();
                state = db.outputStateDao().getOutputStateFromName(jobNumber);
                angles[0] = state.getAngle1();
                angles[1] = state.getAngle2();
                angles[2] = state.getAngle3();
                angles[3] = state.getAngle4();
                activeAngle = 0;
                updateEditTextViews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        angle1RadioButton.setOnClickListener(v -> {
            activeAngle = 0;
            angleForCalc = angles[0];
            clearEditTextViews();
            updateEditTextViews();
        });
        angle2RadioButton.setOnClickListener(v -> {
            activeAngle = 1;
            angleForCalc = angles[1];
            clearEditTextViews();
            updateEditTextViews();
        });
        angle3RadioButton.setOnClickListener(v -> {
            activeAngle = 2;
            angleForCalc = angles[2];
            clearEditTextViews();
            updateEditTextViews();
        });
        angle4RadioButton.setOnClickListener(v -> {
            activeAngle = 3;
            angleForCalc = angles[3];
            clearEditTextViews();
            updateEditTextViews();
        });

        saveButton.setOnClickListener(v -> {
            setAngles();
            db.outputStateDao().update(state);
            Toast.makeText(getApplicationContext(),"Save successful", Toast.LENGTH_SHORT).show();
        });

        cancelButton.setOnClickListener(v -> {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to abandon changes and return to the calculator?");
            alertDialogBuilder.setPositiveButton("Return to Calculator", (dialog, which) -> {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> {
                // do nothing
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

    }

    private void clearEditTextViews() {
        baseEditText.setText("");
        riseEditText.setText("");
        angleEditText.setText("");
    }

    private void updateEditTextViews() {

        updateJobNumberSelection();
        angleForCalc = 0.0;
        for(int i = 0; i < angles.length; i++){
            if(i == activeAngle) {
                angleForCalc = angles[i];
                break;
            }
        }
        setupEditTextDimensions();
        setupAngleRadioButtonText();
    }

    @SuppressLint("DefaultLocale")
    private void setupAngleRadioButtonText() {
        RadioButton[] radioButtons = {angle1RadioButton, angle2RadioButton, angle3RadioButton, angle4RadioButton};
        for(int i = 0; i < angles.length; i++) {
            String angleText = String.format("%.4f",angles[i]);
            String radioButtonText = "Angle " + (i + 1) + " (" + angleText + ")";
            radioButtons[i].setText(radioButtonText);
        }
    }

    @SuppressLint("DefaultLocale")
    private void setupEditTextDimensions() {
        baseDimension = 1.0;
        String baseText = String.format("%.4f", baseDimension);
        baseEditText.setText(baseText);

        riseDimension = Trig.baseToRise(baseDimension, angleForCalc);
        riseDimension = Converters.decimalDimensionToFootDimension(riseDimension);
        String riseText = String.format("%.4f",riseDimension);
        riseEditText.setText(riseText);

        String angleText = String.format("%.4f", angleForCalc);
        angleEditText.setText(angleText);
    }

    @SuppressLint("DefaultLocale")
    private void updateDimensionFromDimensionChange() {
        // If the rise or base is changed, then the angle is changed, based on the
        // base dimension.

        riseDimension = Double.parseDouble(riseEditText.getText().toString());
        riseDimension = Converters.footDimensionToDecimalDimension(riseDimension);

        baseDimension = Double.parseDouble(baseEditText.getText().toString());
        baseDimension = Converters.footDimensionToDecimalDimension(baseDimension);

        angleForCalc = Converters.baseRiseToAngle(baseDimension, riseDimension);
        String angleText = String.format("%.4f", angleForCalc);
        angleEditText.setText(angleText);

        updateAngleRadioButtonText(angleText);
        angles[activeAngle] = angleForCalc;

    }

    @SuppressLint("DefaultLocale")
    private void updateDimensionsOnAngleChange() {
        // If the angle is changed, the base dimension is reset to 1.0000
        // and the rise is calculated, giving the user an 'on 12' roof slope.

        String angleText = angleEditText.getText().toString();
        updateAngleRadioButtonText(angleText);
        angles[activeAngle] = angleForCalc;

        baseDimension = 1.0;
        baseEditText.setText(String.format("%.4f",baseDimension));

        riseDimension = Trig.baseToRise(baseDimension, angleForCalc);
        riseDimension = Converters.decimalDimensionToFootDimension(riseDimension);
        riseEditText.setText(String.format("%.4f",riseDimension));
    }

    private void updateAngleRadioButtonText(String angleText) {
        String angleRadioButtonText = "Angle " + (activeAngle + 1) + " (" + angleText + ")";
        switch (activeAngle) {
            case 0:
                angle1RadioButton.setText(angleRadioButtonText);
                break;
            case 1:
                angle2RadioButton.setText(angleRadioButtonText);
                break;
            case 2:
                angle3RadioButton.setText(angleRadioButtonText);
                break;
            case 3:
                angle4RadioButton.setText(angleRadioButtonText);
                break;
            default:
                break;
        }
    }

    private void updateJobNumberSelection() {

        List<OutputState> states = db.outputStateDao().getAllOutputStates();
        try {
            for (int i = 0; i < states.size(); i++) {
                if (jobNumberSpinner.getAdapter().getItem(i).toString().contains(jobNumber)) {
                    jobNumberSpinner.setSelection(i);
                    return;
                }
            }
        } catch (NullPointerException ex) {
            Log.d("extrasNull", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
    }

    private void setAngles() {
        state.setAngle1(angles[0]);
        state.setAngle2(angles[1]);
        state.setAngle3(angles[2]);
        state.setAngle4(angles[3]);
    }

    private void populateSpinner() {
        try {
            List<OutputState> states = db.outputStateDao().getAllOutputStates();
            String[] stateArray = new String[states.size()];

            for(int i = 0; i < states.size(); i++) {
                stateArray[i] = states.get(i).getName();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, R.layout.spinner_view_layout, R.id.spinnerViewItem, stateArray
            );
            adapter.setDropDownViewResource(R.layout.spinner_view_layout);
            jobNumberSpinner.setAdapter(adapter);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("pop_index", Objects.requireNonNull(ex.getLocalizedMessage()));
        } catch (Exception ex) {
            Log.d("pop_general", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
    }

    private void findAllViews() {
        jobNumberSpinner = findViewById(R.id.jobNumberSpinner);
        riseEditText = findViewById(R.id.riseEditText);
        baseEditText = findViewById(R.id.baseEditText);
        angleEditText = findViewById(R.id.angleValueEditText);
        angle1RadioButton = findViewById(R.id.angle1RadioButtonOnEdit);
        angle2RadioButton = findViewById(R.id.angle2RadioButtonOnEdit);
        angle3RadioButton = findViewById(R.id.angle3RadioButtonOnEdit);
        angle4RadioButton = findViewById(R.id.angle4RadioButtonOnEdit);
        saveButton = findViewById(R.id.saveAnglesButton);
        cancelButton = findViewById(R.id.cancelAnglesButton);
    }
}
