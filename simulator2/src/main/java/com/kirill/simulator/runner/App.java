package com.kirill.simulator.runner;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankConsoleControl;
import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankNNControl;
import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.interfaces.AgentFactory;
import com.kirill.simulator.core.interfaces.ResetFunction;
import com.kirill.simulator.core.math.genetics.AgentFitnessFunction;
import com.kirill.simulator.core.math.genetics.Chromosome;
import com.kirill.simulator.core.math.genetics.ChromosomeManager;
import com.kirill.simulator.core.math.neural.functions.ActivationFunction;
import com.kirill.simulator.core.math.neural.functions.ThActivationFunction;
import com.kirill.simulator.core.sensors.Sharp;
import com.kirill.simulator.core.simulation.Game;
import com.kirill.simulator.core.simulation.Track;
import com.kirill.simulator.view.jogl.JavaDia;
import com.kirill.simulator.view.swing.GameView;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //test();
        AgentFactory simpleTankCreator = new AgentFactory() {
            @Override
            public Agent getNewAgent() {
                int config[]={3,5,5,4};
                ActivationFunction activationFunction = new ThActivationFunction();
                SimpleTankNNControl simpleTankNNControl = new SimpleTankNNControl(config,activationFunction);
                SimpleTank simpleTank = new SimpleTank(simpleTankNNControl);
                simpleTank.setPos(50,25);
                simpleTank.addSharp(new Sharp(5, 80, +Math.PI / 4));
                simpleTank.addSharp(new Sharp(5,80,0));
                simpleTank.addSharp(new Sharp(5, 80, -Math.PI / 4));
                simpleTank.setResetFunction(new ResetFunction() {
                    @Override
                    public void reset(Agent agent) {
                        agent.setPos(50, 25);
                        agent.setDir(1, 0);
                        agent.setLeftOfPath(950);
                    }
                });
                return simpleTank;
            }
        };
        Agent simpleTank = simpleTankCreator.getNewAgent();
        Track tr = new Track(0,0);
        tr.loadFromPNG("g2.png");
        Game game = new Game();
        game.setTrack(tr);
        game.addAgent(simpleTank);
        ChromosomeManager chromosomeManager = new ChromosomeManager(10000,simpleTank.getNumOfGens());
        chromosomeManager.setFitnessFunction(new AgentFitnessFunction(simpleTank,game,simpleTank,800,550));
        chromosomeManager.evolution(5);

        List<Chromosome> best= ((AgentFitnessFunction) (chromosomeManager.getFitnessFunction())).getBest();
        showAll(best);
        /*
        GameView gameView = new GameView(game);
        gameView.startPaint();
        game.startSimulation(0,40);
        game.waitEnd();
        System.out.println( "Hello World!" );*/
    }
    public static void showAll(List<Chromosome> chromosomes){
        Game game = new Game();
        Track track = new Track(0,0);
        track.loadFromPNG("track.png");
        game.setTrack(track);
        int config[]={3,5,5,4};
        for(Chromosome i:chromosomes){
            SimpleTankNNControl simpleTankNNControl = new SimpleTankNNControl(config,new ThActivationFunction());
            simpleTankNNControl.setChromosome(i);
            SimpleTank simpleTank = new SimpleTank(simpleTankNNControl);
            simpleTank.setPos(50,25);
            simpleTank.addSharp(new Sharp(5,80,+Math.PI/4));
            simpleTank.addSharp(new Sharp(5,80,0));
            simpleTank.addSharp(new Sharp(5,80,-Math.PI/4));
            simpleTank.setResetFunction(new ResetFunction() {
                @Override
                public void reset(Agent agent) {
                    agent.setLeftOfPath(Integer.MAX_VALUE);
                    agent.setPos(50,25);
                    agent.setDir(1,0);
                }
            });
            simpleTank.reset();
            game.addAgent(simpleTank);
        }
        //GameView gameView = new GameView(game);
        JavaDia javaDia = new JavaDia(game);
        //gameView.startPaint();
        javaDia.start();
        game.startSimulation(0,10);
        game.waitEnd();
    }
    public static void test(){
        for(;;);
    }
}
