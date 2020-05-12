package com.jedmay.simpledraft.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jedmay.simpledraft.model.UserSettings;
import com.jedmay.simpledraft.repo.UserSettingsRepository;

import java.util.List;

public class UserSettingsViewModel extends AndroidViewModel {

    private UserSettingsRepository mUserSettingsRepository;
    private LiveData<List<UserSettings>> mAllUserSettings;

    public UserSettingsViewModel(Application application) {
        super(application);
        mUserSettingsRepository = new UserSettingsRepository(application);
        mAllUserSettings = mUserSettingsRepository.getmAllUserSettings();
    }

    public LiveData<List<UserSettings>> getAllUserSettings() {return mAllUserSettings;}
    public void insert(UserSettings userSettings) {mUserSettingsRepository.insert(userSettings);}
    public void update(UserSettings userSettings) {mUserSettingsRepository.update(userSettings);}
    public void delete(UserSettings userSettings) {mUserSettingsRepository.delete(userSettings);}

}
