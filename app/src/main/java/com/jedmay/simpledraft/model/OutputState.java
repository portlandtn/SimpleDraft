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

    private double angle1;

    private double angle2;

    private double angle3;

    private double angle4;

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


    public double getAngle1() {
        return angle1;
    }

    public void setAngle1(double angle1) {
        this.angle1 = angle1;
    }

    public double getAngle2() {
        return angle2;
    }

    public void setAngle2(double angle2) {
        this.angle2 = angle2;
    }

    public double getAngle3() {
        return angle3;
    }

    public void setAngle3(double angle3) {
        this.angle3 = angle3;
    }

    public double getAngle4() {
        return angle4;
    }

    public void setAngle4(double angle4) {
        this.angle4 = angle4;
    }
}
