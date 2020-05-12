package com.jedmay.simpledraft.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.dao.UserSettingsDao;
import com.jedmay.simpledraft.db.SimpleDraftDb;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.model.UserSettings;

import java.util.List;

public class UserSettingsRepository {

    private UserSettingsDao userSettingsDao;
    private LiveData<List<UserSettings>> mAllUserSettings;

    public UserSettingsRepository(Application application) {
        SimpleDraftDb db = SimpleDraftDb.getDatabase(application);
        userSettingsDao = db.userSettingsDao();
        mAllUserSettings = userSettingsDao.getAllUserSettings();
    }

    public LiveData<List<UserSettings>> getmAllUserSettings() {
        return mAllUserSettings;
    }

    public void insert(UserSettings userSettings){
        SimpleDraftDb.databaseWriteExecutor.execute(() -> userSettingsDao.insert(userSettings));
    }

    public void update(UserSettings userSettings){
        SimpleDraftDb.databaseWriteExecutor.execute(() -> userSettingsDao.update(userSettings));
    }

    public void delete(UserSettings userSettings){
        SimpleDraftDb.databaseWriteExecutor.execute(() -> userSettingsDao.delete(userSettings));
    }
}
