package com.company.mlp;

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
        //List<BufferedImage> bufImages = new ArrayList<BufferedImage>();
        int config[] = {9*9, 20, 10, 20, 9*9};
        //int config[] = {9*9,1000, 9*9};
        NeuralNetwork nn = new NeuralNetwork(config, new ThActivationFunction());

        Plotter plotter1 = new Plotter();
        Plotter plotter2 = new Plotter();
        List<Image> images = new ArrayList<Image>();

        for(int i = 0;i < 10;i++) {
            Image im = new Image(new File(i+".png"));
            images.add(im);
            double in[] = im.getImageF();
            double out[] = nn.getOut(in);

            plotter1.getBufferedImageList().add(im.getImage());
            plotter1.getBufferedImageList().add(new Image(out,9,9).getImage());
        }



        //plotter.getBufferedImageList().add(image.getImage());
        plotter1.setVisible(true);
        //plotter2.setVisible(true);
    }
}
