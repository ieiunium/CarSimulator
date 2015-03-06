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

    public Game(){

    }
    public void startSimulation(){
        mainThread = new Thread(new SimulationRunnable(this));
        mainThread.start();
    }
    public void startSimulation(int tickLimit){
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
        mainThread = new Thread(new RealTimeSimulationRunnable(this));
        mainThread.start();
    }
    public void startRealTimeSimulation(int tickLimit){
        mainThread = new Thread(new RealTimeSimulationRunnable(this, tickLimit));
        mainThread.start();
    }
    public void tick(){
        boolean carsAreDead = true;
        for(Agent agent: agents){
            if(!agent.collision()) {
                agent.tick();
                carsAreDead = false;
            }
        }
        if(carsAreDead){
            mainThread.stop();
        }
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
    public void addCar(Car car){
        agents.add(car);
        car.setTrack(this.track);
    }
    public List<Agent> getAgents() {
        return agents;
    }
}

