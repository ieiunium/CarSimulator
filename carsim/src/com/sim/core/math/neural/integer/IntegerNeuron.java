package com.sim.core.math.neural.integer;

import java.util.Random;

/**
 * Created by kirill-good on 10.2.15.
 */
public class IntegerNeuron {
    protected int T;
    protected int w[];

    public IntegerNeuron(int n){
        Random random = new Random();
        w = new int[n];
        for(int i = 0;i<w.length;i++){
            w[i] = 0;
        }
    }
    int getOut(int x[]){
        int res = -T;
        for(int i = 0;i<w.length;i++){
            res += w[i]*x[i];
        }
        return res;
    }
}
