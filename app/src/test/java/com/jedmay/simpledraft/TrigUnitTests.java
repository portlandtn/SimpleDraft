package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.Trig;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrigUnitTests {

    private double deltaValue = 0.0001;

    @Test
    public void baseToRiseUnitTest(){
        double baseDimension = 1.0;
        double angle = 4.7636;

        double expected = 0.083333;

        double actual = Trig.baseToRise(baseDimension, angle);

        assertEquals(expected, actual, deltaValue);

    }

    @Test
    public void baseToRiseUnitTestWith2On12RoofSlope(){
        double baseDimension = 2.5;
        double angle = 9.4623;

        double expected = 0.4166667;

        double actual = Trig.baseToRise(baseDimension, angle);

        assertEquals(expected, actual, deltaValue);

    }

    @Test
    public void baseToSlopeUnitTest(){
        double baseDimension = 1.0;
        double angle = 4.7636;

        double expected = 1.003466;

        double actual = Trig.baseToSlope(baseDimension, angle);

        assertEquals(expected, actual, deltaValue);

    }

    @Test
    public void riseToBaseUnitTest() {
        double riseDimension = 0.08333;
        double angle = 4.7636;

        double expected = 1.0;

        double actual = Trig.riseToBase(riseDimension, angle);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void riseToSlopeUnitTest() {
        double riseDimension = 0.08333;
        double angle = 4.7636;

        double expected = 1.003466;

        double actual = Trig.riseToSlope(riseDimension, angle);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void slopeToBaseUnitTest() {
        double slopeDimension = 1.003466;
        double angle = 4.7636;

        double expected = 1.0;

        double actual = Trig.slopeToBase(slopeDimension, angle);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void slopeToRiseUnitTest() {
        double slopeDimension = 1.003466;
        double angle = 4.7636;

        double expected = 0.08333;

        double actual = Trig.slopeToRise(slopeDimension, angle);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void getRoofSlopeFromAngle() {
        double angle = 9.4623;
        double expected = 2;

        double actual = Trig.getRoofSlopeFromAngle(angle);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void getRoofSlopeFromAngleWith1On12() {
        double angle = 4.7636;
        double expected = 1;

        double actual = Trig.getRoofSlopeFromAngle(angle);

        assertEquals(expected, actual, deltaValue);
    }

    @Test
    public void updateAngles() {
        Double angle1 = 14.0208;
        Double angle2 = 1.1935;
        Double angle3 = 11.2222;
        Double angle4 = 2.3859;

        List<Double> existingAngles = new ArrayList<>();
        existingAngles.add(2.2208);
        existingAngles.add(13.2342);
        existingAngles.add(1.2342);
        existingAngles.add(3.8234);

        List<Double> expectedAngles = new ArrayList<>();
        expectedAngles.add(14.0208);
        expectedAngles.add(1.1935);
        expectedAngles.add(11.2222);
        expectedAngles.add(2.3859);

        List<Double> actualAngles = Trig.updateAngles(existingAngles, angle1, angle2, angle3, angle4);

        assertEquals(expectedAngles, actualAngles);
    }

    @Test
    public void updateAnglesWhenAngleListIsNull() {
        Double angle1 = 14.0208;
        Double angle2 = 1.1935;
        Double angle3 = 11.2222;
        Double angle4 = 2.3859;

        List<Double> existingAngles = new ArrayList<>(4);

        List<Double> expectedAngles = new ArrayList<>(4);
        expectedAngles.add(14.0208);
        expectedAngles.add(1.1935);
        expectedAngles.add(11.2222);
        expectedAngles.add(2.3859);

        List<Double> actualAngles = Trig.updateAngles(existingAngles, angle1, angle2, angle3, angle4);

        assertEquals(expectedAngles, actualAngles);
    }

}
