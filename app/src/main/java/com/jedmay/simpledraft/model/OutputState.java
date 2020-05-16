package com.jedmay.simpledraft.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.jedmay.simpledraft.helper.Converters;

import java.util.List;

@Entity(tableName = "output_state")
public class OutputState {

    @TypeConverters(Converters.class)
    private List<Double> values;

    @PrimaryKey
    @NonNull
    private String name;

    @TypeConverters(Converters.class)
    private List<Double> angles;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public List<Double> getAngles() {
        return angles;
    }

    public void setAngles(List<Double> angles) {
        this.angles = angles;
    }
}
