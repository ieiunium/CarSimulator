package com.kirill.simulator.core.simulation;

import com.kirill.simulator.core.interfaces.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 23.2.15.
 */
public class Game {
    private final List<Agent> agents = new ArrayList<Agent>();
    private Track track;
    private Thread mainThread;

    public void waitEnd(){

        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startSimulation(long tickLimit,int millisPerTick){

        mainThread = new Thread( new SimulationRunnable(this,tickLimit,millisPerTick) );
        mainThread.start();
    }

    public void tick(){

        boolean agentsAreDead = true;
        for(Agent agent:agents){
            if(!collision(agent)) {
                agent.tick();
                agentsAreDead = false;
            }
        }
        if(agentsAreDead){
            mainThread.stop();
        }
    }

    public boolean collision(Agent agent){

        return agent.collision();
    }

    public Track getTrack() {

        return track;
    }

    public void setTrack(Track track) {

        this.track = track;
        for(Agent agent:agents){
            agent.setTrack(this.track);
        }
    }

    public void addCar(Agent agent){

        agents.add(agent);
        agent.setTrack(this.track);
    }

    public List<Agent> getCars() {

        return agents;
    }
}