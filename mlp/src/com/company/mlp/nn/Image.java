package com.company.mlp.nn;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kirill-good on 20.3.15.
 */
public class Image {
    private double imageF[];
    private int height;
    private int width;



    public Image(File file){
        try {
            BufferedImage image = ImageIO.read(file);
            height = image.getHeight();
            width = image.getWidth();
            clear();
            for(int i=0;i<height;i++){
                for(int j = 0;j< width;j++){
                    imageF[i*height+j] = image.getRGB(j,i)== Color.BLACK.getRGB()?1:0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(){
        for(int i=0;i<height;i++){
            for(int j = 0;j< width;j++){
                System.out.print((int)(1*imageF[i*height+j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Image(double[] imageF, int height, int width) {
        this.imageF = imageF;
        this.height = height;
        this.width = width;
    }

    private void clear() {
        imageF = new double[height*width];
    }

    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().clearRect(0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (imageF[i*height+j] > 0.9) {
                    image.setRGB(j, i, Color.BLACK.getRGB());
                } else {
                    image.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        }
        return image;
    }

    public double[] getImageF() {
        return imageF;
    }

    public void setImageF(double[] imageF) {
        this.imageF = imageF;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
