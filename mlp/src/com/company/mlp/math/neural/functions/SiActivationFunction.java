package com.company.mlp.math.neural.functions;

/**
 * Created by kirill-good on 29.3.15.
 */
public class SiActivationFunction extends ActivationFunction {
    public double F(double x){
        return (1/(1+Math.exp(-x)));
    }

    @Override
    public double dF(double y) {
        return 0.5*(1-y*y);
    }
}
