package com.company.mlp.math.genetics;

import com.company.mlp.math.neural.NeuralNetwork;
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
    int config[] = {9*9, 20, 10, 20, 9*9};
    //int config[] = {9*9,1000, 9*9};
    NeuralNetwork nn = new NeuralNetwork(config, new ThActivationFunction());
    List<Image> images = new ArrayList<Image>();

    public FitnessFunction() {
        for(int i = 0;i < 10;i++) {
            Image im = new Image(new File(i+".png"));
            images.add(im);
            double in[] = im.getImageF();
        }
    }



    public double fitness(Chromosome chromosome){
        double res = 0;
        nn.setGens(chromosome.getGens());
        for(Image i:images){
            double in[] = i.getImageF();
            double out[] = nn.getOut(in);
            res += getE(in,out);
        }
        return -res;
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
}
