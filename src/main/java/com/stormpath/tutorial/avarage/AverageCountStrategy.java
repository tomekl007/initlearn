package com.stormpath.tutorial.avarage;

/**
 * Created by tomasz.lelek on 20/12/15.
 */
public class AverageCountStrategy {

    public static double countApproxAverage(double currentAverage, double newValue, Integer numberOfSamples) {
        currentAverage -= currentAverage / numberOfSamples;
        currentAverage += newValue / numberOfSamples;
        return currentAverage;
    }
}
