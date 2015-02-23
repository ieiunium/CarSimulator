package com.kirill.simulator.core.interfaces;

/**
 * Created by kirill-good on 23.2.15.
 */
public interface OnlyReadableTrack {
    public boolean getPix(int i,int j);
    public int getWidth();
    public int getHeight();
}
