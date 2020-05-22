package com.jedmay.simpledraft.helper;


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
}
