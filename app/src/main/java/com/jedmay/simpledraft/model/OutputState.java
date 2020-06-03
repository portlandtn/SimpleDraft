package com.jedmay.simpledraft.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.jedmay.simpledraft.helper.Converters;

import java.util.Date;
import java.util.List;

@Entity(tableName = "output_state")
public class OutputState {

    @TypeConverters(Converters.class)
    private List<Double> values;

    @PrimaryKey
    @NonNull
    private String name;

    @TypeConverters(Converters.class)
    private Date createDate;

    @TypeConverters(Converters.class)
    private Date modifiedDate;

    private Double angle1;

    private Double angle2;

    private Double angle3;

    private Double angle4;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public Double getAngle1() {
        return angle1;
    }

    public void setAngle1(Double angle1) {
        this.angle1 = angle1;
    }

    public Double getAngle2() {
        return angle2;
    }

    public void setAngle2(Double angle2) {
        this.angle2 = angle2;
    }

    public Double getAngle3() {
        return angle3;
    }

    public void setAngle3(Double angle3) {
        this.angle3 = angle3;
    }

    public Double getAngle4() {
        return angle4;
    }

    public void setAngle4(Double angle4) {
        this.angle4 = angle4;
    }
}
