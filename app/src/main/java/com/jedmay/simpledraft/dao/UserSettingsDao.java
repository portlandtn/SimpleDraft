package com.jedmay.simpledraft.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jedmay.simpledraft.model.UserSettings;

import java.util.List;

@Dao
public interface UserSettingsDao {

    @Query("SELECT * FROM user_settings")
    LiveData<List<UserSettings>> getAllUserSettings();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(UserSettings userSettings);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(UserSettings userSettings);

    @Delete()
    void delete(UserSettings userSettings);

    @Query("DELETE FROM user_settings")
    void deleteAll();

}
