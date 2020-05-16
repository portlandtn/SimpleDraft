package com.jedmay.simpledraft.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.db.SimpleDraftDb;
import com.jedmay.simpledraft.model.OutputState;

import java.util.List;

public class OutputStateRepository {

    private OutputStateDao outputStateDao;
    private List<OutputState> mAllOutputStates;
    private List<OutputState> mOutputStateValues;

    public OutputStateRepository(Application application, String stateName) {
        SimpleDraftDb db = SimpleDraftDb.getDatabase(application);
        outputStateDao = db.outputStateDao();
        mAllOutputStates = outputStateDao.getAllOutputStates();
        mOutputStateValues = outputStateDao.getOutputStateFromName(stateName);
    }

    public List<OutputState> getmAllOutputStates() {
        return mAllOutputStates;
    }

    public List<OutputState> getmOutputStateValues() {
        return mOutputStateValues;
    }

    public void insert(OutputState outputState){
        SimpleDraftDb.databaseWriteExecutor.execute(() -> outputStateDao.insert(outputState));
    }

    public void update(OutputState outputState){
        SimpleDraftDb.databaseWriteExecutor.execute(() -> outputStateDao.update(outputState));
    }

    public void delete(OutputState outputState){
        SimpleDraftDb.databaseWriteExecutor.execute(() -> outputStateDao.delete(outputState));
    }

    public void deleteAll() {
        SimpleDraftDb.databaseWriteExecutor.execute(() -> outputStateDao.deleteAll());
    }
}
