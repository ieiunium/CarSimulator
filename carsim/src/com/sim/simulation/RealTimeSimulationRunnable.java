package com.sim.simulation;

/**
 * Created by kirill-good on 4.2.15.
 */
public class RealTimeSimulationRunnable implements Runnable{
    private Game game;
    private long millisPerTicks = 10;
    public RealTimeSimulationRunnable(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        long ticks=0;
        long t0=System.currentTimeMillis();
        long time = 0;
        while(true){
            game.tick();

            ticks++;
            time = System.currentTimeMillis()-t0;
            //System.out.println((time/ticks));
            try {
                Thread.sleep(millisPerTicks,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
