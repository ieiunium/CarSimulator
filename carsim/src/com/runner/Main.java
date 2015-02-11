package com.runner;

import com.sim.core.items.Car;
import com.sim.core.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.math.genetics.CarEvolution;
import com.sim.simulation.Game;
import com.sim.core.items.Track;
import com.swing.GameSwingVideoAdapter;
import com.swing.SimpleCarControl;
import com.swing.TrackEditor;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Car car = new Car();
        car.setPos(50,50);
        car.setDir(1, 0);
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(50);
        car.setWidth(3);
        SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
        car.setCarControl(simpleNeuralNetworkControl);
        car.addSharp(new Sharp(5, 110, -Math.PI / 4));
        car.addSharp(new Sharp(5,110,0));
        car.addSharp(new Sharp(5,110,+Math.PI/4));
        car.setLeftOfPath(530);
        Track tr = new Track(600,300);
        tr.loadFromFile("g.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        CarEvolution carEvolution = new CarEvolution(car,game,1000, simpleNeuralNetworkControl);
        carEvolution.evolution(40);
    }
    public static void test(){
        Car car = new Car();
        car.setPos(50,50);
        car.setDir(1, 0);
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(50);
        car.setWidth(3);
        //car.setCarControl(new SimpleNeuralNetworkControl());
        car.setCarControl(new SimpleCarControl());
        car.addSharp(new Sharp(5, 110, -Math.PI / 4));
        car.addSharp(new Sharp(5,110,0));
        car.addSharp(new Sharp(5,110,+Math.PI/4));
        car.setLeftOfPath(530);
        Track tr = new Track(600,300);
        tr.loadFromFile("g.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);

        adapter.startPaint();
        double x = car.getPos().getX();
        double y = car.getPos().getY();
        game.startRealTimeSimulation();
        game.waitEnd();
        adapter.stop();
        double x1 = car.getPos().getX();
        double y1 = car.getPos().getY();
        System.out.println(Math.hypot(x1-x,y1-y));
        while (true);
        //TrackEditor trackEditor = new TrackEditor(tr);
    }
}