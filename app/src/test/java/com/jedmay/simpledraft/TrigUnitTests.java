package com.jedmay.simpledraft;

import com.jedmay.simpledraft.helper.Converters;
import com.jedmay.simpledraft.helper.Trig;
import static org.junit.Assert.*;

import org.junit.Test;

public class TrigUnitTests {

    private double deltaValue = 0.000001;

    @Test
    public void baseToRiseUnitTest(){
        double baseDimension = 1.0;
        double angle = 4.7636;

        double expected = 0.01;

        double actual = Trig.baseToRise(baseDimension, angle);

        assertEquals(expected, actual, deltaValue);

    }


}
