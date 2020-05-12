package com.jedmay.simpledraft.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "user_settings")
public class UserSettings {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private List<OutputState> outputStates;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OutputState> getOutputStates() {
        return outputStates;
    }

    public void setOutputStates(List<OutputState> outputStates) {
        this.outputStates = outputStates;
    }
}
