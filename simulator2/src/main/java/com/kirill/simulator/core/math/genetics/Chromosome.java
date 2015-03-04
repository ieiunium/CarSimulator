package com.kirill.simulator.core.math.genetics;

import java.util.Random;

/**
 * Created by kirill-good on 23.2.15.
 */
public class Chromosome  implements Comparable {

    protected double gens[];
    protected double fitnessValue;
    public static final Random random = new Random();

    public Chromosome(final int numberOfGens){

        gens = new double[numberOfGens];
        for(int i=0;i<gens.length;i++){
            gens[i] = 2 * random.nextDouble() - 1;
        }
    }
    private Chromosome(){

    }

    @Override
    public String toString(){

        StringBuilder stringBuilder = new StringBuilder("[");
        for(double i:gens){
            stringBuilder.append(i+", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Chromosome getCopy(){

        Chromosome chromosome = new Chromosome();
        chromosome.gens = this.gens.clone();
        return chromosome;
    }

    public void crossOver(Chromosome mother,Chromosome father){
        final int border = random.nextInt(gens.length);
        /*int i;
        int p = 0;
        for(i = 0; i < border; i++){
            gens[i] = father.gens[i];
            if(random.nextInt(father.gens.length)<p){
                father.gens[i] = 2 * random.nextDouble() - 1;
            }
            if(random.nextInt(father.gens.length)<p){
                mother.gens[i] = 2 * random.nextDouble() - 1;
            }
        }
        for(;i<this.gens.length;i++){
            gens[i] = mother.gens[i];
            if(random.nextInt(father.gens.length)<p){
                father.gens[i] = 2 * random.nextDouble() - 1;
            }
            if(random.nextInt(father.gens.length)<p){
                mother.gens[i] = 2 * random.nextDouble() - 1;
            }
        }
        */

        for(int i = 0; i < father.gens.length; i++){
            if(random.nextBoolean()){
                gens[i] = father.gens[i];
            }else {
                gens[i] = mother.gens[i];
            }
        }
    }

    @Override
    public int compareTo(Object o) {

        if(o instanceof Chromosome){
            if(((Chromosome)o).fitness() == this.fitness()){
                return 0;
            }else {
                return (((Chromosome) o).fitness() - this.fitness()) > 0 ? 1 : -1;
            }
        }else {
            return 0;
        }
    }

    public double fitness(){

        return fitnessValue;
    }

    public double[] getGens() {
        return gens;
    }
}
