package com.company.mlp.math.neural.functions;

/**
 * Created by kirill-good on 10.2.15.
 */
public abstract class ActivationFunction{
    abstract public double F(double x);
    abstract public double dF(double y);
}
