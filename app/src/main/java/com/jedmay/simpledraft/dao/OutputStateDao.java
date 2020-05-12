package com.jedmay.simpledraft.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jedmay.simpledraft.model.OutputState;

import java.util.List;

@Dao
public interface OutputStateDao {

    @Query("SELECT * FROM output_state")
    LiveData<List<OutputState>> getAllOutputStates();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(OutputState outputState);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(OutputState outputState);

    @Delete()
    void delete(OutputState outputState);

}
