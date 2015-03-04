package com.kirill.simulator.core.simulation;

/**
 * Created by kirill-good on 23.2.15.
 */
public class SimulationRunnable implements Runnable {
    private volatile Game game;
    private long tickLimit;
    private long millisPerTicks;
    public SimulationRunnable(Game game,long tickLimit,int millisPerTick) {

        this.game = game;
        this.tickLimit = tickLimit;
        this.millisPerTicks = millisPerTick;
    }

    @Override
    public void run() {

        if(millisPerTicks==0){
            if(tickLimit==0){
                this.startRun();
            }else{
                this.startRun(tickLimit);
            }
        }else{
            if(tickLimit==0){
                this.realTimeRun();
            }else{
                this.realTimeRun(tickLimit);
            }
        }
    }

    public void realTimeRun(){

        while (true){
            game.tick();
            game.curTic++;
            try {
                Thread.sleep(millisPerTicks,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void realTimeRun(long tickLimit){

        while( tickLimit-- > 0 ){
            game.tick();
            try {
                Thread.sleep(millisPerTicks,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startRun(){

        while (true){
            game.tick();
        }
    }

    public void startRun(long tickLimit){
        while( tickLimit-- > 0 ){
            game.tick();
        }
    }
}
