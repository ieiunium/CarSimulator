package com.kirill.simulator.core.math.neural.functions;

/**
 * Created by kirill-good on 23.2.15.
 */
public class ThActivationFunction extends ActivationFunction {

    @Override
    public double F(double x) {

        return 2/(1+Math.exp(-x))-1;
    }
}
