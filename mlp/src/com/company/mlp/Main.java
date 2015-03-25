package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.ThActivationFunction;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;

import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        long t = System.currentTimeMillis();

        int config[] = {9*9, 20, 10, 20, 9*9};
        NeuralNetwork nn = new NeuralNetwork(config, new ThActivationFunction());
        System.out.println(nn.numOfGens());
        ChromosomeManager chromosomeManager = new ChromosomeManager(1,nn.numOfGens(),new FitnessFunction());
        chromosomeManager.mutationOnly(100);
        Chromosome chromosomes[] = chromosomeManager.getChromosomes();

        for(Chromosome chr:chromosomes) {
            nn.setGens(chr.getGens());
            Plotter plotter1 = new Plotter();
            List<Image> images = new ArrayList<Image>();
            for (int i = 0; i < 10; i++) {
                Image im = new Image(new File(i + ".png"));
                images.add(im);
                double in[] = im.getImageF();
                double out[] = nn.getOut(in);
                for (int j = 0; j < in.length; j++) {
                    System.out.println(in[i]+" "+out[i]);
                }
                System.out.println();
                //plotter1.getBufferedImageList().add(im.getImage());
                plotter1.getBufferedImageList().add(im.getImage());
                plotter1.getBufferedImageList().add(new Image(out, 9, 9).getImage());
            }
            plotter1.setVisible(true);
        }
        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
}
