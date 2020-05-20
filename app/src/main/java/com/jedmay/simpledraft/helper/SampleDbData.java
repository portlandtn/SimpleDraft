package com.jedmay.simpledraft.helper;

import android.content.Context;
import android.util.Log;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.model.OutputState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SampleDbData {

    private SimpleDraftDbBadCompany db;

    public SampleDbData(Context context) {
        db = SimpleDraftDbBadCompany.getDatabase(context);
    }

    public void populateDbWithSampleData() {
        try {
            db.outputStateDao().insert(setupState1());
            db.outputStateDao().insert(setupState2());
            db.outputStateDao().insert(setupState3());
        } catch (Exception ex) {
            Log.d("pop_db", Objects.requireNonNull(ex.getLocalizedMessage()));
        }

    }

    private OutputState setupState1() {
        OutputState state = new OutputState();
        state.setName("K12J0208");

        List<Double> angles = new ArrayList<>();
        angles.add(14.0208);
        angles.add(31.0809);
        angles.add(11.0208);
        angles.add(4.7636);
        state.setAngles(angles);

        List<Double> stateList = new ArrayList<>();
        stateList.add(31.0208);
        stateList.add(9.0809);
        stateList.add(-12.0204);
        stateList.add(-11.0001);
        stateList.add(13.0603);
        state.setAngles(angles);

        state.setValues(stateList);

        return state;

    }

    private OutputState setupState2() {
        OutputState state = new OutputState();
        state.setName("K12J0209");

        List<Double> angles = new ArrayList<>();
        angles.add(4.7636);
        angles.add(14.0809);
        angles.add(15.0208);
        angles.add(2.3859);
        state.setAngles(angles);

        List<Double> stateList = new ArrayList<>();
        stateList.add(1.0204);
        stateList.add(1.0204);
        stateList.add(-2.0408);

        state.setValues(stateList);

        return state;

    }

    private OutputState setupState3() {
        OutputState state = new OutputState();
        state.setName("K19L0413");

        List<Double> angles = new ArrayList<>();
        angles.add(4.7636);
        angles.add(1.4932);
        angles.add(14.0623);
        angles.add(0.0);

        List<Double> stateList = new ArrayList<>();
        stateList.add(11.0010);
        stateList.add(-15.0208);
        stateList.add(16.0005);
        stateList.add(-21.0408);
        state.setAngles(angles);

        state.setValues(stateList);

        return state;

    }

}
