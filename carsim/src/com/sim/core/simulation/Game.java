package com.sim.core.simulation;

import com.sim.core.agents.car.Car;
import com.sim.core.interfaces.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 4.2.15.
 */
public class Game{


    private final List<Agent> agents = new ArrayList<Agent>();
    private Track track;
    private Thread mainThread;
    private int curTick;
    public Game(){

    }
    public void startSimulation(){
        curTick = 0;
        mainThread = new Thread(new SimulationRunnable(this));
        mainThread.start();
    }
    public void startSimulation(int tickLimit){
        curTick = 0;
        mainThread = new Thread(new SimulationRunnable(this,tickLimit));
        mainThread.start();
    }
    public void waitEnd(){
        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void startRealTimeSimulation(){
        curTick = 0;
        mainThread = new Thread(new RealTimeSimulationRunnable(this));
        mainThread.start();
    }
    public void startRealTimeSimulation(int tickLimit){
        curTick = 0;
        mainThread = new Thread(new RealTimeSimulationRunnable(this, tickLimit));
        mainThread.start();
    }
    public void tick(){
        boolean carsAreDead = true;
        for(Agent agent: agents){
            if(!agent.collision()) {
                agent.tick();
                if(agent.getLeftOfPath()>0){
                    carsAreDead = false;
                }
            }

        }
        if(carsAreDead){
            mainThread.stop();
        }
        curTick ++;
    }

    public Track getTrack() {
        return track;
    }
    public void setTrack(Track track) {
        this.track = track;
        for(Agent agent: agents){
            agent.setTrack(this.track);
        }
    }
    public void addAgent(Agent agent){
        agents.add(agent);
        agent.setTrack(this.track);
    }
    public List<Agent> getAgents() {
        return agents;
    }

    public int getCurTick() {
        return curTick;
    }
}

