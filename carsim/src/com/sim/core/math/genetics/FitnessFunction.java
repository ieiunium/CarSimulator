package com.sim.core.math.genetics;

/**
 * Created by kirill-good on 11.2.15.
 */
public class FitnessFunction {
    public double fitness(Chromosome chromosome){
        double res = 0;
        for(double i: chromosome.gens){
            res += i;
        }
        return res;
    }
}
