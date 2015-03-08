package com.sim.core.math.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill-good on 11.2.15.
 */
public class ChromosomeManager {

    protected Chromosome chromosomes[] = null;
    protected FitnessFunction fitnessFunction;

    public ChromosomeManager(final int numberOfChromosome,int gensPerChromosome,FitnessFunction fitnessFunction){
        this.fitnessFunction = fitnessFunction;
        chromosomes = new Chromosome[numberOfChromosome];
        for(int i = 0;i< numberOfChromosome;i++){
            chromosomes[i]= new Chromosome(gensPerChromosome);
            chromosomes[i].setFitnessFunction(fitnessFunction);
        }
    }

    public void evolution(int steps){

        Chromosome children[] = chromosomes.clone();
        for(int step = 0; step < steps; step++) {

            for(Chromosome i: chromosomes){
                i.calcFitness();
            }
            Arrays.sort(chromosomes);
            System.out.println(step + " " + chromosomes[0].fitness());
            int half = chromosomes.length / 2;
            for (int i = 0; i < half; i++) {

                int i1 = Chromosome.random.nextInt(half);
                int i2;
                do{
                    i2 = Chromosome.random.nextInt(half);
                }while (i1==i2);
                Chromosome d1 = chromosomes[i1].getCopy();
                Chromosome d2 = chromosomes[i2].getCopy();
                Chromosome.crossOver(d1, d2);
                children[i * 2] = d1;
                children[i * 2 + 1] = d2;
            }
            Chromosome tmp[] = children;
            children = chromosomes;
            chromosomes = tmp;
        }
    }
}
