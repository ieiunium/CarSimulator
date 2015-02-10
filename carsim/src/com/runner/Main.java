package com.runner;

import com.sim.core.items.Car;
import com.sim.core.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.simulation.Game;
import com.sim.core.items.Track;
import com.swing.GameSwingVideoAdapter;

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
        //SimpleCarControl simpleCarControl = new SimpleCarControl();
        car.setCarControl(new SimpleNeuralNetworkControl());
        car.addSharp(new Sharp(5,110,-Math.PI/4));
        car.addSharp(new Sharp(5,110,0));
        car.addSharp(new Sharp(5,110,+Math.PI/4));

        Track tr = new Track(600,300);
        tr.loadFromFile("g.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);

        adapter.startPaint();
        game.startRealTimeSimulation();
        game.waitEnd();
        adapter.stop();
        //TrackEditor trackEditor = new TrackEditor(tr);
    }
}