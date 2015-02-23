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

    public static void crossOver(Chromosome mother,Chromosome father){

        if(mother.gens.length!=father.gens.length){
            return;
        }
        final int border = random.nextInt(mother.gens.length);
        for(int i = 0; i < border; i++){
            double  tmp = father.gens[i];
            father.gens[i] = mother.gens[i];
            mother.gens[i] = tmp;
        }
        for(int i = 0; i < father.gens.length; i++){
            if(random.nextInt(father.gens.length)<10){
                father.gens[i] = ( random.nextBoolean()?1:-1 ) * random.nextDouble();
            }
            if(random.nextInt(father.gens.length)<10){
                mother.gens[i] = ( random.nextBoolean()?1:-1 ) * random.nextDouble();
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
}
