package com.sim.core.Sensors;

/**
 * Created by kirill-good on 6.2.15.
 */
public class Sharp {
    public final double max;
    public final double min = 1;
    public final double angle;
    protected double value;
    public Sharp(double max, double angle) {
        this.max = max;
        this.angle = angle;
    }
    public double getValue(){
        return value;
    }
    public Sharp getCopy(){
        return new Sharp(max,angle);
    }
}
