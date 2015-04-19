package com.company.mlp.math.genetics;

import com.company.mlp.math.neural.Example;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.SiActivationFunction;
import com.company.mlp.math.neural.functions.ThActivationFunction;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 11.2.15.
 */
public class FitnessFunction {
    protected NeuralNetwork nn;
    protected List<Example> learningSet;

    public FitnessFunction(NeuralNetwork nn, List<Example> learningSet) {
        this.nn = nn;
        this.learningSet = learningSet;
    }

    public double fitness(Chromosome chromosome){
        double res = 0;
        nn.setGens(chromosome.getGens());
        for(Example i: learningSet){
            res += i.getE(nn.getOut(i.getIn()));
        }
        return -res/learningSet.size();
    }
    public static double getE(double in[],double out[]){
        double res = 0;
        if(in.length!=out.length){
            throw new RuntimeException("in.length!=out.length");
        }
        for (int i = 0; i < in.length; i++) {
            res += Math.pow(in[i]-out[i],2);
        }
        return res;
    }

    public void testNN(Chromosome chromosome){
        nn.setGens(chromosome.getGens());
        System.out.println("out\tetalon");
        for(Example i: learningSet){
            double out[] = nn.getOut(i.getIn());
            for (int j = 0; j < out.length; j++) {
                System.out.printf("%.2f\t",out[j]);
            }
            System.out.println();
            for (int j = 0; j < i.getOut().length; j++) {
                System.out.printf("%.2f\t",i.getOut()[j]);
            }
            System.out.println();
            System.out.print("E = ");
            System.out.println(i.getE(out));
            System.out.println("===================");
        }
    }
}
