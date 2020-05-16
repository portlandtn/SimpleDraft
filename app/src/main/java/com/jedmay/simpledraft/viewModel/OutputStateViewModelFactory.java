package com.jedmay.simpledraft.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.repo.OutputStateRepository;

import java.util.List;

public class OutputStateViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mStateName;

    private OutputStateRepository mOutputStateRepository;
    private List<OutputState> mAllOutputStates;
    private List<OutputState> mOutputStateValues;

    public OutputStateViewModelFactory(Application application, String stateName) {
        mApplication = application;
        mStateName = stateName;
        mOutputStateRepository = new OutputStateRepository(application, stateName);
        mAllOutputStates = mOutputStateRepository.getmAllOutputStates();
        mOutputStateValues = mOutputStateRepository.getmOutputStateValues();
    }

    public List<OutputState> getAllOutputStates() {return mAllOutputStates;}
    public List<OutputState> getOutputStateValues() {return mOutputStateValues;}

    public void insert(OutputState outputState) { mOutputStateRepository.insert(outputState);}
    public void update(OutputState outputState) { mOutputStateRepository.update(outputState);}
    public void delete(OutputState outputState) { mOutputStateRepository.delete(outputState);}
    public void deleteAll() {mOutputStateRepository.deleteAll();}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OutputStateViewModel(mApplication, mStateName);
    }
}
