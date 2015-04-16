package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.Example;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.SiActivationFunction;
import com.company.mlp.math.neural.functions.ThActivationFunction;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;

import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainXOR {
    static Random random = new Random();
    public static void main(String[] args) {
	// write your code here
        long t = System.currentTimeMillis();
        List<Example> learningSet = Arrays.asList(new Example[]{
                new Example(new double[]{0,0},new double[]{0}),
                new Example(new double[]{0,1},new double[]{1}),
                new Example(new double[]{1,0},new double[]{1}),
                new Example(new double[]{1,1},new double[]{0}),
        });

        int config[] = {2,3,1};
        NeuralNetwork nn = new NeuralNetwork(config, new SiActivationFunction());
        System.out.println(nn.numOfGens());
        FitnessFunction fitnessFunction = new FitnessFunction(nn,learningSet);

        ChromosomeManager chromosomeManager = new ChromosomeManager(1,nn.numOfGens(),fitnessFunction);
        chromosomeManager.mutationOnly(-0.0028,100);
        Chromosome chromosomes[] = chromosomeManager.getChromosomes();
        fitnessFunction.testNN(chromosomes[0]);
        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
    public static List<Example> loadMnist(){
        List<Example> res = new ArrayList<Example>();

        return res;
    }
}
