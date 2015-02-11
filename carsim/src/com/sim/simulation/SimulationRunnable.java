package com.sim.simulation;

/**
 * Created by kirill-good on 4.2.15.
 */
public class SimulationRunnable implements Runnable{
    private volatile Game game;
    private final int tickLimit;
    public SimulationRunnable(Game game) {
        this.game = game;
        this.tickLimit = 0;
    }
    public SimulationRunnable(Game game,int tickLimit) {
        this.game = game;
        this.tickLimit = tickLimit;
    }
    @Override
    public void run() {
        if(tickLimit < 1){
            while (true){
                game.tick();
            }
        }else {
            for (int i = 0; i < tickLimit; i++) {
                game.tick();
            }
        }
    }
}
