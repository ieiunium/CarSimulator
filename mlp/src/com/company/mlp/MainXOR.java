package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.ThActivationFunction;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 28.3.15.
 */
public class MainXOR {
    static double dataIn[][] = {
         {0,0}
        ,{0,1}
        ,{1,0}
        ,{1,1}
    };
    static double dataOut[][] = {
            {0}
            ,{1}
            ,{1}
            ,{0}
    };
    public static void main(String[] args) {
        // write your code here
        long t = System.currentTimeMillis();

        int config[] = {2,2,1};
        NeuralNetwork nn = new NeuralNetwork(config, new ThActivationFunction(){
            public double F(double x) {
                return 1/(1+Math.exp(-x));
            }
        });
        System.out.println(nn.numOfGens());
        ChromosomeManager chromosomeManager = new ChromosomeManager(1,nn.numOfGens(),new FitnessFunction(){
            int config[] = {2,2,1};
            NeuralNetwork nn = new NeuralNetwork(config, new ThActivationFunction(){
                public double F(double x) {
                    return 1/(1+Math.exp(-x));
                }
            });

            @Override
            public double fitness(Chromosome chromosome) {
                nn.setGens(chromosome.getGens());
                double E = 0;
                for (int i = 0; i < dataIn.length; i++) {
                    double out[] = nn.getOut(dataIn[i]);
                    E += Math.pow(out[0]-dataOut[i][0],2);
                }

                return -E;
            }
        });
        chromosomeManager.mutationOnly(-0.000003,1000);
        Chromosome chromosomes[] = chromosomeManager.getChromosomes();
        nn.setGens(chromosomes[0].getGens());
        for (int i = 0; i < dataIn.length; i++) {
            double out[] = nn.getOut(dataIn[i]);
            System.out.println(dataIn[i][0]+" "+dataIn[i][1]+" "+out[0]);
        }

        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
}
