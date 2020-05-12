package com.jedmay.simpledraft.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.jedmay.simpledraft.helper.Converters;

import java.util.List;

@Entity(tableName = "output_state")
public class OutputState {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @TypeConverters(Converters.class)
    private List<Double> values;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}
