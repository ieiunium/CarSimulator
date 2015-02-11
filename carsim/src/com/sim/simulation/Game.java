package com.sim.simulation;

import com.sim.core.items.Car;
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
            if(!collision(car)) {
                car.tick();
                carsAreDead = false;
            }
        }
        if(carsAreDead){
            mainThread.stop();
        }
    }

    public boolean collision(Car car){
        int x1 = (int)car.getPos().getX();
        int y1 = (int)car.getPos().getY();
        int x2 = (int)car.getHeadX();
        int y2 = (int)car.getHeadY();
        boolean res=false;
        if( track.getPix(x1,y1) || track.getPix(x2,y2)){
            res=true;
        }
        return res;
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

