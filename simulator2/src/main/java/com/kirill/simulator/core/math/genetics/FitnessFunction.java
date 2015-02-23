package com.kirill.simulator.core.math.genetics;

import com.kirill.simulator.core.math.genetics.Chromosome;

/**
 * Created by kirill-good on 23.2.15.
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
