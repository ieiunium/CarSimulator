package com.runner;

import com.sim.core.agents.car.Car;
import com.sim.core.agents.car.NNCarFactory;
import com.sim.core.Sensors.Sharp;
import com.sim.core.agents.tank.NNTankConrtol;
import com.sim.core.agents.tank.Tank;
import com.sim.core.agents.tank.TankState;
import com.sim.core.interfaces.Agent;
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

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        testTank();
        teachingNNCar();
        test();
    }
    public static void testTank(){
        Game game = new Game();
        Track track = new Track(1,1);
        track.loadFromFile("g2.map");
        game.setTrack(track);


        Tank tank = new Tank();
        tank.setWidth(10);
        tank.setLength(50);
        tank.setTankState(TankState.STOP);
        tank.setPos(50,50);
        tank.setDir(1,0);
        int []config={3,4};
        NNTankConrtol nnTankConrtol = new NNTankConrtol(config,new ActivationFunction());
        tank.setTankControl(nnTankConrtol);
        tank.addSharp(new Sharp(80, -Math.PI / 4));
        tank.addSharp(new Sharp(80, 0));
        tank.addSharp(new Sharp(80, +Math.PI / 4));
        tank.setColor(Color.BLACK);
        tank.setResetFunction(new ResetFunction() {
            @Override
            public void reset(Agent agent) {
                agent.setPos(50, 50);
                agent.setDir(1, 0);
                agent.setLeftOfPath(900);
            }
        });
        game.addAgent(tank);

        AgentFitnessFunction fitnessFunction = new AgentFitnessFunction();
        fitnessFunction.setAgent(tank);
        fitnessFunction.setGame(game);
        fitnessFunction.setTresHold(700);
        fitnessFunction.setTickLimit(2000);
        ChromosomeManager chromosomeManager = new ChromosomeManager(10000,tank.getNumOfGens(),fitnessFunction);
        chromosomeManager.evolution(1);

        List<Chromosome> chromosomes = fitnessFunction.getChromosomeList();
        GameSwingVideoAdapter gameSwingVideoAdapter = new GameSwingVideoAdapter(game);

        for(Chromosome i:chromosomes) {
            tank.reset();
            tank.setChromosome(i);
            tank.setLeftOfPath(Integer.MAX_VALUE);
            gameSwingVideoAdapter.startPaint();
            game.startRealTimeSimulation(1000);
            game.waitEnd();
        }

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
        agents.addAll(carBuilder(fitnessFunction.getChromosomeList(),nnCarFactory));


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

        agents.addAll(carBuilder(fitnessFunction.getChromosomeList(),nnCarFactory));
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
        while (true);
    }
    public static void editor(){
        Track tr = new Track(1000,800);
        //tr.loadFromFile("g.map");
        TrackEditor trackEditor = new TrackEditor(tr);
        while (true);

    }
    public static List<Agent> carBuilder(List<Chromosome> list,NNCarFactory nnCarFactory){
        List<Agent> res = new ArrayList<Agent>();
        for(Chromosome i:list){
            Agent car = nnCarFactory.getNewAgent();
            car.setChromosome(i.getCopy());
            res.add(car);
        }
        return res;
    }

    public static void showAllCar(List<Chromosome> list,Track track, NNCarFactory nnCarFactory){
        System.out.println(list.size());
        Game game = new Game();
        game.setTrack(track);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();
        for(Chromosome i:list){
            Car car = (Car)nnCarFactory.getNewAgent();
            double gens[] = new double[i.getGens().length];

            System.out.print(car.getId() + "[ ");
            for(int j = 0;j<gens.length;j++){
                gens[j] = (int)(i.getGens()[j]*100);
                System.out.print(gens[j] + " ");
            }
            System.out.println("]");
            car.setChromosome(i.getCopy());
            game.addAgent(car);
            //System.out.println(car.getId() + " " + i.toString());
        }
        game.startRealTimeSimulation(10000);
        game.waitEnd();
    }
}