package com.company.mlp.math.genetics;

import java.util.*;

/**
 * Created by kirill-good on 11.2.15.
 */
public class ChromosomeManager {

    protected Chromosome chromosomes[] = null;
    protected FitnessFunction fitnessFunction;
    private volatile double fitnessT = Double.NaN;
    private volatile double stepT = Double.NaN;

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

    public void mutationOnly(double E,int steps){
        //Chromosome children[] = chromosomes.clone();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(stepT + " " + fitnessT);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        List<Chromosome> all = new LinkedList<Chromosome>();

        for(Chromosome i: chromosomes){
            i.calcFitness();
        }

        double d = 0.01;

        Chromosome ch = chromosomes[0].getCopy();
        for(int step = 0; step < steps || chromosomes[0].fitness() < E; step++) {
            d = 0.1 / (1+(int)(step/300));

            for (int i = 0; i < chromosomes.length; i++) {

                ch.setGens(chromosomes[i]);
                ch.mutation( d );
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
            stepT = step;
            fitnessT = -chromosomes[0].fitness();
            //System.out.println(step + " " + chromosomes[0].fitness() + " " +p);
        }
        t.stop();
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
        for(Chromosome i: chromosomes){
            i.setFitnessFunction(fitnessFunction);
        }
    }
}
