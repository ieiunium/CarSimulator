package com.runner;

import com.sim.core.agents.car.Car;
import com.sim.core.agents.car.NNCarFactory;
import com.sim.core.Sensors.Sharp;
import com.sim.core.agents.tank.NNTankConrtol;
import com.sim.core.agents.tank.NNTankFactory;
import com.sim.core.agents.tank.Tank;
import com.sim.core.agents.tank.TankState;
import com.sim.core.interfaces.Agent;
import com.sim.core.interfaces.AgentFactory;
import com.sim.core.interfaces.Chromosomal;
import com.sim.core.interfaces.ResetFunction;
import com.sim.core.math.genetics.AgentFitnessFunction;
import com.sim.core.math.genetics.Chromosome;
import com.sim.core.math.genetics.ChromosomeManager;
import com.sim.core.math.neural.functions.ActivationFunction;
import com.sim.core.math.neural.functions.ThActivationFunction;
import com.sim.core.simulation.Game;
import com.sim.core.simulation.Track;
import com.swing.GameSwingVideoAdapter;
import com.swing.TrackEditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.sim.core.simulation.AgentServices.*;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        //testTank2();
        testCar2();
    }
    public static void testCar2(){

        NNCarFactory nnCarFactory = new NNCarFactory();
        nnCarFactory.setPos(50,50);
        nnCarFactory.setDir(1, 0);
        nnCarFactory.setMaxWheelsAngle(Math.PI / 3);
        nnCarFactory.setMaxSpeed(2);
        nnCarFactory.setLength(50);
        nnCarFactory.setWidth(3);
        int []config={3,2};
        nnCarFactory.setConfigNN(config);
        nnCarFactory.setColor(Color.RED);
        nnCarFactory.setActivationFunction(new ActivationFunction());
        nnCarFactory.getSharpList().add(new Sharp(80, -Math.PI / 4));
        nnCarFactory.getSharpList().add(new Sharp(80,0));
        nnCarFactory.getSharpList().add(new Sharp(80, +Math.PI / 4));
        nnCarFactory.setLeftOfPath(Integer.MAX_VALUE);
        nnCarFactory.setResetFunction(new ResetFunction() {
            @Override
            public void reset(Agent agent) {
                agent.setPos(50,50);
                agent.setDir(1, 0);
                agent.setLeftOfPath(1100);
            }
        });

        Track track = new Track(0,0);
        //track.loadFromFile("g2.map");
        track.loadFromPNG("g3.png");
        Track track2 = new Track(0,0);
        track2.loadFromFile("track.map");
        //track2.loadFromPNG("g3.png");
        List<Agent> agents = teachAgents(900,10, 20, nnCarFactory, track);
        int []config2={3,20,2};
        nnCarFactory.setConfigNN(config2);
        nnCarFactory.setActivationFunction(new ThActivationFunction());
        nnCarFactory.setColor(Color.GREEN);
        agents.addAll(teachAgents(900,10, 150, nnCarFactory, track));
        runAgents(10000, agents, track2);
    }
    public static void testTank2(){
        NNTankFactory nnTankFactory = new NNTankFactory();
        nnTankFactory.setWidth(10);
        nnTankFactory.setLength(50);
        nnTankFactory.setDirx(1);
        nnTankFactory.setDiry(0);
        nnTankFactory.setPosx(50);
        nnTankFactory.setPosy(50);
        int []config={3,4};
        nnTankFactory.setAngleTurn(Math.PI/8);
        nnTankFactory.setConfigNN(config);
        nnTankFactory.setActivationFunction(new ActivationFunction());
        nnTankFactory.getSharpList().add(new Sharp(80, -Math.PI / 4));
        nnTankFactory.getSharpList().add(new Sharp(80, 0));
        nnTankFactory.getSharpList().add(new Sharp(80, +Math.PI / 4));
        nnTankFactory.setColor(Color.BLACK);
        nnTankFactory.setResetFunction(new ResetFunction() {
            @Override
            public void reset(Agent agent) {
                agent.setPos(50, 50);
                agent.setDir(1, 0);
                agent.setLeftOfPath(900);
            }
        });
        Track track = new Track(0,0);
        track.loadFromFile("g2.map");
        Track track2 = new Track(0,0);
        //track2.loadFromFile("track.map");
        track2.loadFromPNG("g3.png");
        List<Agent> agents = teachAgents(700,10, 100, nnTankFactory, track);
        runAgents(10000,agents,track2);
        test();
    }
    public static void teachingNNCar(){
        int []config={3,2};
        NNCarFactory nnCarFactory = new NNCarFactory();
        nnCarFactory.setPos(50,50);
        nnCarFactory.setDir(1, 0);
        nnCarFactory.setMaxWheelsAngle(Math.PI / 3);
        nnCarFactory.setMaxSpeed(2);
        nnCarFactory.setLength(50);
        nnCarFactory.setWidth(3);
        nnCarFactory.setConfigNN(config);
        nnCarFactory.setActivationFunction(new ActivationFunction());
        nnCarFactory.getSharpList().add(new Sharp(80, -Math.PI / 4));
        nnCarFactory.getSharpList().add(new Sharp(80,0));
        nnCarFactory.getSharpList().add(new Sharp(80, +Math.PI / 4));
        nnCarFactory.setLeftOfPath(Integer.MAX_VALUE);
        nnCarFactory.setColor(Color.RED);
        nnCarFactory.setResetFunction(new ResetFunction() {
            @Override
            public void reset(Agent agent) {
                agent.setPos(50,50);
                agent.setDir(1, 0);
                agent.setLeftOfPath(1100);
            }
        });
        Agent car = nnCarFactory.getNewAgent();
        Track tr = new Track(600,300);

        tr.loadFromFile("g2.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addAgent(car);
        AgentFitnessFunction fitnessFunction = new AgentFitnessFunction();
        fitnessFunction.setAgent(car);
        fitnessFunction.setGame(game);
        fitnessFunction.setTresHold(850);
        fitnessFunction.setTickLimit(2000);
        ChromosomeManager chromosomeManager = new ChromosomeManager(5000,car.getNumOfGens(),fitnessFunction);
        chromosomeManager.evolution(1);

        List<Agent> agents = new ArrayList<Agent>();
        agents.addAll(agentBuilder(fitnessFunction.getChromosomeList(),nnCarFactory));


        int config2[] = {3,5,2};
        nnCarFactory.setColor(Color.GREEN);
        nnCarFactory.setConfigNN(config2);
        nnCarFactory.setActivationFunction(new ThActivationFunction());

        car = nnCarFactory.getNewAgent();
        game = new Game();
        game.setTrack(tr);
        game.addAgent(car);
        fitnessFunction = new AgentFitnessFunction();
        fitnessFunction.setAgent(car);
        fitnessFunction.setGame(game);
        fitnessFunction.setTresHold(500);
        fitnessFunction.setTickLimit(2000);
        chromosomeManager = new ChromosomeManager(5000,car.getNumOfGens(),fitnessFunction);
        chromosomeManager.evolution(1);

        agents.addAll(agentBuilder(fitnessFunction.getChromosomeList(),nnCarFactory));
        System.out.println(fitnessFunction.getChromosomeList().size());

        tr.loadFromFile("megatrack.map");
        game = new Game();
        game.getAgents().addAll(agents);
        game.setTrack(tr);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();
        game.startRealTimeSimulation(1000000);
        game.waitEnd();
    }
    public static void test(){
        System.out.println("end");
        while (true);
    }
    public static void editor(){
        Track tr = new Track(1000,800);
        //tr.loadFromFile("g.map");
        TrackEditor trackEditor = new TrackEditor(tr);
        while (true);
    }

}