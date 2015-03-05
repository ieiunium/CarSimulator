package com.sim.core.math.genetics;

import com.sim.core.interfaces.Chromosomal;

import java.util.Random;

/**
 * Created by kirill-good on 11.2.15.
 */
public class Chromosome implements Comparable {
    protected double gens[];
    private FitnessFunction fitnessFunction = new FitnessFunction();
    private double fitnessValue;
    public static final Random random = new Random();
    public Chromosome(final int numberOfGens){
        gens = new double[numberOfGens];
        for(int i=0;i<gens.length;i++){
            gens[i] = random.nextBoolean()?-1:1 * random.nextDouble();
        }
    }

    public Chromosome(double[] gens) {
        this.gens = gens;
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
        //father.gens[random.nextInt(mother.gens.length)] = ( random.nextBoolean()?1:-1 ) * random.nextDouble();
        //mother.gens[random.nextInt(mother.gens.length)] = ( random.nextBoolean()?1:-1 ) * random.nextDouble();
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

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
    public void calcFitness(){
        fitnessValue = fitnessFunction.fitness(this);
    }
    public double fitness(){
        return fitnessValue;
    }

    public double[] getGens() {
        return gens;
    }

    public void setGens(double[] gens) {
        this.gens = gens;
    }
}
