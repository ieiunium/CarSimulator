package com.kirill.simulator.core.interfaces;

/**
 * Created by kirill-good on 23.2.15.
 */
public interface Agent {
    public void tick();
    public void setTrack(OnlyReadableTrack onlyReadableTrack);
    public boolean collision();
}
