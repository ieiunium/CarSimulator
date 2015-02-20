package com.runner;

import com.sim.core.interfaces.CarControl;
import com.sim.core.items.Car;
import com.sim.core.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.items.Tank;
import com.sim.core.math.genetics.CarEvolution;
import com.sim.core.math.genetics.Chromosome;
import com.sim.simulation.Game;
import com.sim.core.items.Track;
import com.swing.GameSwingVideoAdapter;
import com.swing.SimpleCarControl;
import com.swing.TrackEditor;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //test();
        Car car = new Car();
        car.setPos(50,50);
        car.setDir(1, 0);
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(50);
        car.setWidth(3);
        SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
        car.setCarControl(simpleNeuralNetworkControl);
        car.addSharp(new Sharp(5, 80, -Math.PI / 4));
        car.addSharp(new Sharp(5,80,0));
        car.addSharp(new Sharp(5,80,+Math.PI/4));
        car.setLeftOfPath(530);
        Track tr = new Track(600,300);
        tr.loadFromFile("g2.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        CarEvolution carEvolution = new CarEvolution(car,game,10000, simpleNeuralNetworkControl);
        carEvolution.evolution(10);
        List<Chromosome> chromosomeList = carEvolution.getCopyOfBestCarChromosome();
        tr.loadFromFile("track.map");
        //carEvolution.removeCrashed(chromosomeList,tr);

        carEvolution.showAll(chromosomeList, tr);

    }
    public static void test(){
        Car car = new Tank();
        car.setPos(50,50);
        car.setDir(1, 0);
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(50);
        car.setWidth(20);
        SimpleCarControl simpleCarControl = new SimpleCarControl();
        SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
        car.setCarControl(simpleCarControl);
        car.addSharp(new Sharp(5, 80, -Math.PI / 4));
        car.addSharp(new Sharp(5,80,0));
        car.addSharp(new Sharp(5,80,+Math.PI/4));
        car.setLeftOfPath(Integer.MAX_VALUE);
        Track tr = new Track(600,300);
        tr.loadFromFile("track.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();
        game.startRealTimeSimulation();
        game.waitEnd();
        System.out.println(simpleNeuralNetworkControl);
        while (true);
    }
    public static void editor(){
        Track tr = new Track(1000,800);
        //tr.loadFromFile("g.map");
        TrackEditor trackEditor = new TrackEditor(tr);
        while (true);

    }
}