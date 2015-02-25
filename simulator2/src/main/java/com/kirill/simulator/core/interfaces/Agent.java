package com.kirill.simulator.core.interfaces;

import java.awt.*;

/**
 * Created by kirill-good on 23.2.15.
 */
public interface Agent {
    public void tick();
    public void setTrack(OnlyReadableTrack onlyReadableTrack);
    public boolean collision();
    public void paint(Graphics g,int dx,int dy);
    void setPos(double x,double y);
}
