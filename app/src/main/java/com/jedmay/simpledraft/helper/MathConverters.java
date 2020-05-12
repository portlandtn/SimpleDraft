package com.jedmay.simpledraft.helper;

public class MathConverters {

    public static double extractWholeNumberFromDimension(double dimension) {
        return (int) dimension;
    }

    public static double extractInchesFromDimension(double dimension) {

        // Remove whole feet:
        dimension -= extractWholeNumberFromDimension(dimension);

        //convert inch from decimal to whole inch:
        dimension *= 100;

        //Truncate sixteenths off
        dimension -= extractWholeNumberFromDimension(dimension);
        dimension /= 100;
        return ((int) dimension);
    }

    public static double extractSixteenthsFromDimension(double dimension) {

        // Remove whole feet
        dimension -= extractWholeNumberFromDimension(dimension);

        //convert inch from decimal to whole inch:
        dimension *= 100;

        // Remove whole inches
        dimension -= extractWholeNumberFromDimension(dimension);

        // convert sixteenths back to thousandths place
        dimension /= 100;

        return dimension;

    }
}
