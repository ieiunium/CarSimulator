package com.sim.core.interfaces;

import com.sim.core.math.genetics.Chromosome;

/**
 * Created by kirill-good on 6.3.15.
 */
public interface Chromosomal {
    public void setChromosome(Chromosome chromosome);
    public Chromosome getChromosome();
    public int getNumOfGens();
}
