package com.sim.core;

import com.sim.core.items.Car;
import com.sim.core.items.Track;
import com.sim.simulation.RealTimeSimulationRunnable;
import com.sim.simulation.SimulationRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 4.2.15.
 */
public class Game{


    private final List<Car> cars = new ArrayList<Car>();
    private Track track = new Track(100,100);
    private Thread mainThread;

    public Game(){

    }
    public void startSimulation(){
        mainThread = new Thread(new SimulationRunnable(this));
        mainThread.start();
    }
    public void startRealTimeSimulation(){
        mainThread = new Thread(new RealTimeSimulationRunnable(this));
        mainThread.start();
    }
    public void tick(){
        for(Car c:cars){
            c.tick();
        }
    }
    public void addCar(Car car){
        cars.add(car);
    }

    public int getTrackX() {
        return track.getM();
    }
    public int getTrackY() {
        return track.getN();
    }
    public List<Car> getCars() {
        return cars;
    }
}

