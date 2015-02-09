package com.sim.core.interfaces;

/**
 * Created by kirill-good on 6.2.15.
 */
public interface OnlyReadableTrack {
    public boolean getPix(int i,int j);
    public int getWidth();
    public int getHeight();
}
