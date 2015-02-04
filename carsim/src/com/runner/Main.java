package com.runner;

import com.sim.core.items.Car;
import com.sim.core.Game;
import com.sim.core.items.Track;
import com.sim.core.items.math.Vector2f;
import com.swing.GameSwingVideoAdapter;
import com.swing.SimpleCarControl;
import com.swing.TrackEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    public static void main(String[] args) {
	// write your code here

        SimpleCarControl simpleCarControl = new SimpleCarControl();
        Car car = new Car(new Vector2f(50,50),new Vector2f(1,0),Math.PI/3,2,50,3,simpleCarControl);

        Track tr = new Track(600,300);
        tr.loadFromFile("track.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);

        adapter.startPaint();
        game.startRealTimeSimulation();

        //TrackEditor trackEditor = new TrackEditor(new Track(640,480));
    }
}