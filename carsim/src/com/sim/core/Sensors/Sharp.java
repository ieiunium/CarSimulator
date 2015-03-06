package com.sim.core.Sensors;

/**
 * Created by kirill-good on 6.2.15.
 */
public class Sharp {
    public final double max;
    public final double min;
    public final double angle;
    protected double value;
    public Sharp(double min, double max, double angle) {
        this.max = max;
        this.min = min;
        this.angle = angle;
    }
    public double getValue(){
        return value;
    }
    public Sharp getCopy(){
        return new Sharp(min,max,angle);
    }
}
