package com.sim.core.math.neural;

import java.util.Random;

/**
 * Created by kirill-good on 10.2.15.
 */
public class Neuron{
    protected double T;
    protected double w[];
    protected ActivationFunction activationFunction = new ActivationFunction();

    public Neuron(int n){
        Random random = new Random();
        w = new double[n];
        T = (random.nextBoolean()?1:-1) * random.nextDouble();
        for(int i = 0;i<w.length;i++){
            w[i] = (random.nextBoolean()?1:-1) * random.nextDouble();
        }
    }
    double getOut(double x[]){
        double res = -T;
        for(int i = 0;i<w.length;i++){
            res += w[i]*x[i];
        }
        return activationFunction.F(res);
    }
}
