package com.sim.core.math.neural;

/**
 * Created by kirill-good on 10.2.15.
 */
public class ActivationFunction{
    public double F(double x){
        return x;
        //return (2/(1+Math.exp(-x)))-1;
    }
}
