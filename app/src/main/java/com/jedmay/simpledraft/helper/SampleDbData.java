package com.jedmay.simpledraft.helper;

import android.content.Context;
import android.util.Log;

import com.jedmay.simpledraft.db.SimpleDraftDb;
import com.jedmay.simpledraft.model.OutputState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SampleDbData {

    private SimpleDraftDb db;

    public SampleDbData(Context context) {
        db = SimpleDraftDb.getDatabase(context);
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

        state.setAngle1(14.0208);
        state.setAngle2(31.0809);
        state.setAngle3(11.0208);
        state.setAngle4(4.7636);

        List<Double> stateList = new ArrayList<>();
        stateList.add(31.0208);
        stateList.add(9.0809);
        stateList.add(-12.0204);
        stateList.add(-11.0001);
        stateList.add(13.0603);

        state.setValues(stateList);

        return state;

    }

    private OutputState setupState2() {
        OutputState state = new OutputState();
        state.setName("K12J0209");

        state.setAngle1(4.7636);
        state.setAngle2(14.0809);
        state.setAngle3(15.0208);
        state.setAngle4(2.3859);

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

        state.setAngle1(4.7636);
        state.setAngle2(1.4932);
        state.setAngle3(14.0623);
        state.setAngle4(0.0);

        List<Double> stateList = new ArrayList<>();
        stateList.add(11.0010);
        stateList.add(-15.0208);
        stateList.add(16.0005);
        stateList.add(-21.0408);

        state.setValues(stateList);

        return state;

    }

}
