package com.company.mlp.nn;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 20.3.15.
 */
public class Plotter extends JFrame{
    private List<BufferedImage> bufferedImageList = new ArrayList<BufferedImage>();

    public Plotter(){
        this.setSize(400,150);
        //this.setSize(bufferedImage.getWidth(),bufferedImage.getHeight());
    }

    private int k = 3;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0,0,this.getWidth(),this.getHeight());
        int dx = 20,dy = 40;

        for (int i = 0; i < bufferedImageList.size(); i++) {
            int w = bufferedImageList.get(i).getWidth()*k;
            int h = bufferedImageList.get(i).getHeight()*k+5;
            g.drawImage(bufferedImageList.get(i)
                    ,dx + (i/2) * w + i*5
                    ,dy + (i%2) * h
                    ,bufferedImageList.get(i).getWidth()*k
                    ,bufferedImageList.get(i).getHeight()*k
                    ,null);

        }
        //g.drawImage(bufferedImage,40+0,40+0,null);
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public List<BufferedImage> getBufferedImageList() {
        return bufferedImageList;
    }

    public void setBufferedImageList(List<BufferedImage> bufferedImageList) {
        this.bufferedImageList = bufferedImageList;
    }
}
