package com.sim.simulation;

/**
 * Created by kirill-good on 4.2.15.
 */
public class SimulationRunnable implements Runnable{
    private volatile Game game;

    public SimulationRunnable(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while(true){
            game.tick();
        }
    }
}
