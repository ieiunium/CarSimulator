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
        this.setSize(1200,200);
        //this.setSize(bufferedImage.getWidth(),bufferedImage.getHeight());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0,0,this.getWidth(),this.getHeight());
        int dx = 40,dy = 40;
        for (int i = 0; i < bufferedImageList.size(); i++) {
            g.drawImage(bufferedImageList.get(i)
                    ,dx + i * 50
                    ,dy + 0
                    ,bufferedImageList.get(i).getWidth()*3
                    ,bufferedImageList.get(i).getHeight()*3
                    ,null);

        }
        //g.drawImage(bufferedImage,40+0,40+0,null);
    }

    public List<BufferedImage> getBufferedImageList() {
        return bufferedImageList;
    }

    public void setBufferedImageList(List<BufferedImage> bufferedImageList) {
        this.bufferedImageList = bufferedImageList;
    }
}
