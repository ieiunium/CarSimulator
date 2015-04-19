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
import java.util.List;
import java.util.Random;

/**
 * Created by kirill on 19.4.15.
 */
public class Main {
    static Random random = new Random();
    static String src = "./t10k-images-9x9/";
    public static void main(String[] args) {
        // write your code here
        long t = System.currentTimeMillis();
        List<Example> learningSet = loadMnist(6g);
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
    public static List<Example> loadMnist(int imagesNum){
        List<Example> res = new ArrayList<Example>();
        for (int i = 0; i < 10; i++) {
            for (int c = 0; c < imagesNum; c++) {
                try {
                    String path = src+String.valueOf(i)+"_"+ String.valueOf(c) + ".bmp";
                    System.out.println(i+" "+path);
                    double out[] = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    out[i] = 1;
                    res.add(new Example(openPicture(path), out));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
    public static double[] openPicture(String path) throws IOException {
        BufferedImage j = ImageIO.read(new File(path));
        double in[] = new double[j.getWidth() * j.getHeight()];
        for (int k = 0; k < j.getWidth(); k++) {
            for (int l = 0; l < j.getHeight(); l++) {
                Color mycolor = new Color(j.getRGB(k, l));
                double R = mycolor.getRed();
                double G = mycolor.getGreen();
                double B = mycolor.getBlue();
                in[k * j.getHeight() + l] = (0.299 * R + 0.587 * G + 0.114 * B) / 256.0;
                //in[k * j.getHeight() + l] = j.getRGB(k, l) == Color.BLACK.getRGB() ? 1 : 0;
                //System.out.println(in[k*j.getHeight()+l] );
            }
        }
        return in;
    }

}