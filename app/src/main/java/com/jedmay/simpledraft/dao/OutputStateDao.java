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
    List<OutputState> getAllOutputStates();

    @Query("SELECT * FROM output_state where name =:stateName")
    List<OutputState> getOutputStateFromName(String stateName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(OutputState outputState);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(OutputState outputState);

    @Delete()
    void delete(OutputState outputState);

    @Query("DELETE FROM output_state")
    void deleteAll();

}
