package com.sim.core.interfaces;

import com.sim.core.Sensors.Sharp;
import com.sim.core.Sensors.SharpManager;

import java.awt.*;

/**
 * Created by kirill-good on 6.3.15.
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
    //public void glPaint(GL2 gl,int dX,int dY,int dZ);
    public double getX();
    public double getY();
    public void reset();
    public void setResetFunction(ResetFunction resetFunction);
    public double getLeftOfPath();
    public void setLeftOfPath(double leftOfPath);
}
