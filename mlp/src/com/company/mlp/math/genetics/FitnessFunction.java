package com.company.mlp.math.genetics;

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
    protected int config[] = {9*9, 20, 10, 20, 9*9};
    //int config[] = {9*9,1000, 9*9};
    protected NeuralNetwork nn = new NeuralNetwork(config, new SiActivationFunction());
    protected List<Image> images = new ArrayList<Image>();

    public FitnessFunction(String path) {
        for(int i = 0;i < 10;i++) {
            Image im = new Image(new File(path+i+".png"));
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
        return -res/images.size();
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
        Plotter plotter1 = new Plotter();
        for (int i = 0; i < images.size(); i++) {
            double in[] = images.get(i).getImageF();
            double out[] = nn.getOut(in);
            for (int j = 0; j < nn.getLayer()[1].getOuts().length; j++) {
                System.out.print((int) (nn.getLayer()[1].getOuts()[j] * 10) + " ");
            }
            System.out.println();
            int a = (int)Math.sqrt(in.length);
            plotter1.getBufferedImageList().add(new Image(in, a, a).getImage());
            plotter1.getBufferedImageList().add(new Image(out, a, a).getImage());
        }
        plotter1.setVisible(true);
    }

    public void testMidLayer(Chromosome chromosome){
        nn.setGens(chromosome.getGens());
        Plotter plotter1 = new Plotter();

        for (int i = 0; i < images.size(); i++) {
            double res[] = new double[10];
            for (int j = 0; j < res.length; j++) {
                res[j]=0;
                nn.getLayer()[1].getOuts()[j] = 0;
            }
            res[i]=1;
            nn.getLayer()[1].getOuts()[i] = 1;
            for(int j = 2;j<nn.getLayer().length;j++){
                res = nn.getLayer()[j].getOut( res );
            }
            for (int j = 0; j < nn.getLayer()[1].getOuts().length; j++) {
                System.out.print( (int)(nn.getLayer()[1].getOuts()[j]*10)+" ");
            }
            System.out.println();
            int a = (int)Math.sqrt(images.get(i).getImageF().length);
            plotter1.getBufferedImageList().add(new Image(res, a, a).getImage());
            plotter1.getBufferedImageList().add(new Image(res, a, a).getImage());
        }
        plotter1.setVisible(true);
    }

    public int[] getConfig() {
        return config;
    }

    public void setConfig(int[] config) {
        nn = new NeuralNetwork(config, new SiActivationFunction());
        this.config = config;
    }
}
