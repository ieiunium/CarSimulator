package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.Example;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.SiActivationFunction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by kirill on 16.4.15.
 */
public class MainSimpleNN {
    static Random random = new Random();
    public static void main(String[] args) {
        // write your code here
        long t = System.currentTimeMillis();
        List<Example> learningSet = loadMnist();
        //System.exit(0);
        int config[] = {9*9,30,20,10};
        NeuralNetwork nn = new NeuralNetwork(config, new SiActivationFunction());
        System.out.println(nn.numOfGens());
        FitnessFunction fitnessFunction = new FitnessFunction(nn,learningSet);

        ChromosomeManager chromosomeManager = new ChromosomeManager(1,nn.numOfGens(),fitnessFunction);
        chromosomeManager.mutationOnly(-0.05,100);
        Chromosome chromosomes[] = chromosomeManager.getChromosomes();
        fitnessFunction.testNN(chromosomes[0]);
        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
    public static List<Example> loadMnist(){
        List<Example> res = new ArrayList<Example>();
        for (int i = 0; i < 10; i++) {
            try {
                BufferedImage j = ImageIO.read(new File(String.valueOf(i)+".png"));
                double in[] = new double[j.getWidth()*j.getHeight()];
                for (int k = 0; k < j.getWidth(); k++) {
                    for (int l = 0; l < j.getHeight(); l++) {
                        in[k*j.getHeight()+l] = j.getRGB(k,l) == Color.BLACK.getRGB()?1:0;;
                        //System.out.println(in[k*j.getHeight()+l] );
                    }
                }
                double out[] = new double[]{0,0,0,0,0,0,0,0,0,0};
                out[i] = 1;
                res.add(new Example(in,out));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}

