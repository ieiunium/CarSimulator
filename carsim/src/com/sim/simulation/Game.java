package com.sim.simulation;

import com.sim.core.agents.Car;
import com.sim.core.items.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 4.2.15.
 */
public class Game{


    private final List<Car> cars = new ArrayList<Car>();
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
        for(Car car:cars){
            if(!car.collision()) {
                car.tick();
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
        for(Car car:cars){
            car.setTrack(this.track);
        }
    }
    public void addCar(Car car){
        cars.add(car);
        car.setTrack(this.track);
    }
    public List<Car> getCars() {
        return cars;
    }
}

