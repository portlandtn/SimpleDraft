package com.jedmay.simpledraft.helper;


import java.util.ArrayList;
import java.util.List;

public class Trig {


    public static double baseToRise(double baseDimension, double angle) {
        return baseDimension * Math.tan(Math.toRadians(angle));
    }

    public static double baseToSlope(double baseDimension, double angle) {
        return baseDimension / Math.cos(Math.toRadians(angle));
    }

    public static double riseToBase(double riseDimension, double angle) {
        return riseDimension / Math.tan(Math.toRadians(angle));
    }

    public static double riseToSlope(double riseDimension, double angle) {
        return riseDimension / Math.sin(Math.toRadians(angle));
    }

    public static double slopeToBase(double slopeDimension, double angle) {
        return slopeDimension * Math.cos(Math.toRadians(angle));
    }

    public static double slopeToRise(double slopeDimension, double angle) {
        return slopeDimension * Math.sin(Math.toRadians(angle));
    }

    public static double getRoofSlopeFromAngle(double angle) {
        final double baseDimensionOn12 = 1;
        return (baseToRise(baseDimensionOn12, angle)) * 12;
    }

    public static List<Double> updateAngles(List<Double> angles, Double angle1, Double angle2, Double angle3, Double angle4) {
        if (angles == null || angles.size() == 0) {
            angles = new ArrayList<>(4);
            angles.add(angle1);
            angles.add(angle2);
            angles.add(angle3);
            angles.add(angle4);
        } else {
            angles.set(0, angle1);
            angles.set(1, angle2);
            angles.set(2, angle3);
            angles.set(3, angle4);
        }

        return angles;
    }
}
