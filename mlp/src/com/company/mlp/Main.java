package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.SiActivationFunction;
import com.company.mlp.math.neural.functions.ThActivationFunction;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;

import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static Random random = new Random();
    public static void main(String[] args) {
	// write your code here
        long t = System.currentTimeMillis();

        int config[] = {9*9, 20, 10, 20, 9*9};
        NeuralNetwork nn = new NeuralNetwork(config, new SiActivationFunction());
        System.out.println(nn.numOfGens());
        FitnessFunction fitnessFunction = new FitnessFunction("");

        ChromosomeManager chromosomeManager = new ChromosomeManager(1,nn.numOfGens(),fitnessFunction);
        //fitnessFunction.testNN(chromosomeManager.getChromosomes()[0]);
        fitnessFunction.testMidLayer(chromosomeManager.getChromosomes()[0]);
        chromosomeManager.mutationOnly(-0.028,100);
        Chromosome chromosomes[] = chromosomeManager.getChromosomes();
        for (int k = 0; k < 1; k++) {
            for(Chromosome chr:chromosomes) {
                fitnessFunction.testNN(chr);
                fitnessFunction.testMidLayer(chromosomeManager.getChromosomes()[0]);
            }
        }

        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
}
