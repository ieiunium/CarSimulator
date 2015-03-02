package com.kirill.simulator.core.interfaces;

import com.kirill.simulator.core.math.genetics.Chromosome;

/**
 * Created by kirill-good on 2.3.15.
 */
public interface Chromosomal {
    public void setChromosome(Chromosome chromosome);
    public Chromosome getChromosome();
    public int getNumOfGens();
}
