package com.jedmay.simpledraft.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "output_state")
public class OutputState {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private List<Float> values;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Float> getValues() {
        return values;
    }

    public void setValues(List<Float> values) {
        this.values = values;
    }
}
