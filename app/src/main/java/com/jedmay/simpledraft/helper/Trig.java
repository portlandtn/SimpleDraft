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

    public static List<Double> updateAngles(List<Double> originalAngles, List<Double> newAngles) {
        if (originalAngles == null || originalAngles.size() == 0) {
            originalAngles = new ArrayList<>(4);
            originalAngles.add(newAngles.get(0));
            originalAngles.add(newAngles.get(1));
            originalAngles.add(newAngles.get(2));
            originalAngles.add(newAngles.get(3));
        } else {
            originalAngles.set(0, newAngles.get(0));
            originalAngles.set(1, newAngles.get(1));
            originalAngles.set(2, newAngles.get(2));
            originalAngles.set(3, newAngles.get(3));
        }

        return originalAngles;
    }
}
