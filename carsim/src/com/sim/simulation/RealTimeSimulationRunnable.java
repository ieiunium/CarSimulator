package com.sim.simulation;

/**
 * Created by kirill-good on 4.2.15.
 */
public class RealTimeSimulationRunnable implements Runnable{
    private Game game;
    private long millisPerTicks = 10;
    private final int tickLimit;
    public RealTimeSimulationRunnable(Game game) {
        this.game = game;
        this.tickLimit = 0;
    }
    public RealTimeSimulationRunnable(Game game,int tickLimit) {
        this.game = game;
        this.tickLimit = tickLimit;
    }
    @Override
    public void run() {
        long ticks=0;
        long t0=System.currentTimeMillis();
        long time = 0;

        if(tickLimit < 1){
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
        }else {
            for (int i = 0; i < tickLimit; i++) {
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
}
