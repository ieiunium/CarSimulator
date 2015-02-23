package com.kirill.simulator.core.math.neural;

import com.kirill.simulator.core.math.neural.functions.ActivationFunction;

import java.util.Random;

/**
 * Created by kirill-good on 23.2.15.
 */
public class Neuron {
    protected double T;
    protected double w[];
    protected ActivationFunction activationFunction;

    public Neuron(int n,ActivationFunction activationFunction){

        this.activationFunction = activationFunction;
        Random random = new Random();
        w = new double[n];
        T = random.nextDouble() * 2 - 1;
        for(int i = 0;i<w.length;i++){
            w[i] = random.nextDouble() * 2 - 1;
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
