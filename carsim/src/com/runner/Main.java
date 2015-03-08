package com.runner;

import com.sim.core.agents.car.Car;
import com.sim.core.agents.car.NNCarFactory;
import com.sim.core.agents.car.SimpleNNCarControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.interfaces.Agent;
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
import java.util.List;

public class Main {
    static int []config={3,2};
    public static void main(String[] args) {
        teachingNNCar();
        test();
    }
    public static void teachingNNCar(){
        NNCarFactory nnCarFactory = new NNCarFactory();
        nnCarFactory.setPos(50,50);
        nnCarFactory.setDir(1, 0);
        nnCarFactory.setMaxWheelsAngle(Math.PI / 3);
        nnCarFactory.setMaxSpeed(2);
        nnCarFactory.setLength(50);
        nnCarFactory.setWidth(3);
        nnCarFactory.setConfigNN(config);
        nnCarFactory.setActivationFunction(new ThActivationFunction());
        nnCarFactory.getSharpList().add(new Sharp(5, 80, -Math.PI / 4));
        nnCarFactory.getSharpList().add(new Sharp(5,80,0));
        nnCarFactory.getSharpList().add(new Sharp(5, 80, +Math.PI / 4));
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
        fitnessFunction.setTresHold(600);
        fitnessFunction.setTickLimit(2000);
        ChromosomeManager chromosomeManager = new ChromosomeManager(10000,car.getNumOfGens(),fitnessFunction);
        chromosomeManager.evolution(1);
        List<Chromosome> chromosomeList = fitnessFunction.getChromosomeList();
        tr.loadFromFile("megatrack.map");
        showAll(chromosomeList, tr, nnCarFactory);
    }
    public static void test(){
        while (true);
    }
    public static void editor(){
        Track tr = new Track(1000,800);
        //tr.loadFromFile("g.map");
        TrackEditor trackEditor = new TrackEditor(tr);
        while (true);

    }public static void showAll(List<Chromosome> list,Track track, NNCarFactory nnCarFactory){
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