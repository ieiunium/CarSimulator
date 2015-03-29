package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.SiActivationFunction;
import com.company.mlp.math.neural.functions.ThActivationFunction;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 29.3.15.
 */
public class MainBPL {
    public static void main(String arg[]){
        long t = System.currentTimeMillis();

        int config[] = {9*9, 20, 10, 20, 9*9};
        NeuralNetwork nn = new NeuralNetwork(config, new SiActivationFunction());

        double inn[][] = new double[10][];
        for (int i = 0; i < 10; i++) {
            Image im = new Image(new File(i + ".png"));
            inn[i] = im.getImageF().clone();
        }
        nn.bpl(inn,inn,0.3,0.01);


        Plotter plotter1 = new Plotter();
        List<Image> images = new ArrayList<Image>();
        for (int i = 0; i < 10; i++) {
            Image im = new Image(new File(i + ".png"));
            images.add(im);
            double in[] = im.getImageF().clone();
            double out[] = nn.getOut(in);
            for (int j = 0; j < in.length; j++) {
                System.out.println(in[j]+" "+out[j]);
            }
            System.out.println();
            plotter1.getBufferedImageList().add(new Image(in, 9, 9).getImage());
            plotter1.getBufferedImageList().add(new Image(out, 9, 9).getImage());
        }
        plotter1.setVisible(true);

        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
}
