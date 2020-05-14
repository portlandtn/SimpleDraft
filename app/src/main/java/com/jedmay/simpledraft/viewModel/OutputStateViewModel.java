package com.jedmay.simpledraft.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.repo.OutputStateRepository;

import java.util.List;

public class OutputStateViewModel extends AndroidViewModel {

    private OutputStateRepository mOutputStateRepository;
    private LiveData<List<OutputState>> mAllOutputStates;

    public OutputStateViewModel(Application application) {
        super(application);
        mOutputStateRepository = new OutputStateRepository(application);
        mAllOutputStates = mOutputStateRepository.getmAllOutputStates();
    }

    LiveData<List<OutputState>> getAllOutputStates() {return mAllOutputStates;}

    public void insert(OutputState outputState) { mOutputStateRepository.insert(outputState);}
    public void update(OutputState outputState) { mOutputStateRepository.update(outputState);}
    public void delete(OutputState outputState) { mOutputStateRepository.delete(outputState);}
    public void deleteAll() {mOutputStateRepository.deleteAll();}
}
