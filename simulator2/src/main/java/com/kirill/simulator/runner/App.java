package com.kirill.simulator.runner;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankConsoleControl;
import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankNNControl;
import com.kirill.simulator.core.sensors.Sharp;
import com.kirill.simulator.core.simulation.Game;
import com.kirill.simulator.core.simulation.Track;
import com.kirill.simulator.view.jogl.JavaDia;
import com.kirill.simulator.view.swing.GameView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        test();

        SimpleTankConsoleControl simpleTankConsoleControl = new SimpleTankConsoleControl();
        SimpleTank simpleTank = new SimpleTank(simpleTankConsoleControl);
        simpleTank.setPos(50,25);
        Track tr = new Track(0,0);
        tr.loadFromPNG("track.png");
        Game game = new Game();
        game.setTrack(tr);
        game.addAgent(simpleTank);
        GameView gameView = new GameView(game);
        gameView.startPaint();
        game.startSimulation(0,40);
        game.waitEnd();
        System.out.println( "Hello World!" );
    }
    public static void test(){
        Track tr = new Track(0,0);
        tr.loadFromPNG("megatrack.png");
        Game game = new Game();
        game.setTrack(tr);
        //SimpleTankConsoleControl simpleTankConsoleControl = new SimpleTankConsoleControl();
        for(int i=0;i<1000;i++){
            SimpleTankNNControl simpleTankConsoleControl = new SimpleTankNNControl ();
            SimpleTank simpleTank = new SimpleTank(simpleTankConsoleControl);
            simpleTank.addSharp(new Sharp(5,80,+Math.PI/4));
            simpleTank.addSharp(new Sharp(5,80,0));
            simpleTank.addSharp(new Sharp(5,80,-Math.PI/4));
            simpleTank.setPos(60,60);
            game.addAgent(simpleTank);
        }
        JavaDia javaDia = new JavaDia(game);
        GameView gameView = new GameView(game);
        //gameView.startPaint();
        game.startSimulation(0,0);

        javaDia.start();

        for(;;);
    }
}
