package com.jedmay.simpledraft.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.repo.OutputStateRepository;

import java.util.List;

public class outputFact implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mStateName;

    private OutputStateRepository mOutputStateRepository;
    private LiveData<List<OutputState>> mAllOutputStates;
    private LiveData<List<OutputState>> mOutputStateValues;

    public outputFact(Application application, String stateName) {
        mApplication = application;
        mStateName = stateName;
        mOutputStateRepository = new OutputStateRepository(application, stateName);
        mAllOutputStates = mOutputStateRepository.getmAllOutputStates();
        mOutputStateValues = mOutputStateRepository.getmOutputStateValues();
    }

    public LiveData<List<OutputState>> getAllOutputStates() {return mAllOutputStates;}
    public LiveData<List<OutputState>> getOutputStateValues() {return mOutputStateValues;}

    public void insert(OutputState outputState) { mOutputStateRepository.insert(outputState);}
    public void update(OutputState outputState) { mOutputStateRepository.update(outputState);}
    public void delete(OutputState outputState) { mOutputStateRepository.delete(outputState);}
    public void deleteAll() {mOutputStateRepository.deleteAll();}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ViewMod(mApplication, mStateName);
    }
}
