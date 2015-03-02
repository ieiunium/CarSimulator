package com.kirill.simulator.core.interfaces;

import com.kirill.simulator.core.math.genetics.Chromosome;
import com.kirill.simulator.core.sensors.Sharp;
import com.kirill.simulator.core.sensors.SharpManager;

import javax.media.opengl.GL2;
import java.awt.*;

/**
 * Created by kirill-good on 23.2.15.
 */
public interface Agent {
    public void tick();
    public void setTrack(OnlyReadableTrack onlyReadableTrack);
    public boolean collision();
    public void paint(Graphics g,int dx,int dy);
    public void setPos(double x,double y);
    public void setDir(double x,double y);
    public SharpManager getSharpManager();
    public void addSharp(Sharp sharp);
    public void glPaint(GL2 gl,int dX,int dY,int dZ);
    public double getX();
    public double getY();
    public void reset();
}
