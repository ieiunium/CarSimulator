package com.company.mlp.math.genetics;

import java.util.*;

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
            for (int i = 0; i < chromosomes.length; i++) {

                int i1 = Chromosome.random.nextInt(half);
                int i2;
                double s;
                do{
                    i2 = Chromosome.random.nextInt(half);
                }while (i1==i2);
                Chromosome d1 = chromosomes[i1].getCopy();
                Chromosome d2 = chromosomes[i2].getCopy();
                children[i] = Chromosome.crossOver(d1, d2);
                //System.out.println("\t"+ Chromosome.dist(d1,d2));
            }
            Chromosome tmp[] = children;
            children = chromosomes;
            chromosomes = tmp;
        }
    }

    public void mutationOnly(int steps){
        //Chromosome children[] = chromosomes.clone();
        List<Chromosome> all = new LinkedList<Chromosome>();

        for(Chromosome i: chromosomes){
            i.calcFitness();
        }
        //double p=0.0003;
        double p=1.5*1.0/chromosomes[0].gens.length;

        Chromosome ch = chromosomes[0].getCopy();
        for(int step = 0; step < steps || chromosomes[0].fitness() < -0.3; step++) {

            for (int i = 0; i < chromosomes.length; i++) {

                //p = ((double)(step%50)/100.0);
                ch.setGens(chromosomes[i]);
                ch.mutation( p );
                ch.calcFitness();
                //System.out.println("[" + etalon.fitness()+" "+ch.fitness()+ " ] ");
                if(ch.getFitnessValue()>chromosomes[i].getFitnessValue()){
                    Chromosome tmp = chromosomes[i];
                    chromosomes[i] = ch;
                    ch = tmp;
                }else{

                }
            }
            //Arrays.sort(chromosomes);
            System.out.println(step + " " + chromosomes[0].fitness() + " " +p);
        }
    }

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
