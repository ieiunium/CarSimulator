package com.kirill.simulator.runner;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.agents.controls.simpletank.SimpleSimpleTankConsoleControl;
import com.kirill.simulator.core.simulation.Game;
import com.kirill.simulator.core.simulation.Track;
import com.kirill.simulator.view.swing.GameView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SimpleSimpleTankConsoleControl simpleTankConsoleControl = new SimpleSimpleTankConsoleControl();
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
}
