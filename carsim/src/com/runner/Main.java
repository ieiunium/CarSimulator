package com.runner;

import com.sim.core.agents.car.Car;
import com.sim.core.agents.car.NNCarFactory;
import com.sim.core.agents.car.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.interfaces.Agent;
import com.sim.core.interfaces.ResetFunction;
import com.sim.core.math.genetics.AgentFitnessFunction;
import com.sim.core.math.genetics.Chromosome;
import com.sim.core.math.genetics.ChromosomeManager;
import com.sim.core.math.neural.functions.ThActivationFunction;
import com.sim.core.simulation.Game;
import com.sim.core.simulation.Track;
import com.swing.GameSwingVideoAdapter;
import com.swing.TrackEditor;

import java.util.List;

public class Main {
    static int []config={3,2};
    public static void main(String[] args) {
	// write your code here
        //test();
        //testSimpleTank();
        //teachingNNCar();
        //test();
        Car car = new Car();
        car.setPos(50, 50);
        car.setDir(1, 0);
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(50);
        car.setWidth(3);

        SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl(config,new ThActivationFunction());
        car.setCarControl(simpleNeuralNetworkControl);
        car.addSharp(new Sharp(5, 80, -Math.PI / 4));
        car.addSharp(new Sharp(5,80,0));
        car.addSharp(new Sharp(5, 80, +Math.PI / 4));
        car.setLeftOfPath(530);
        car.setResetFunction(new ResetFunction() {
            @Override
            public void reset(Agent agent) {
                agent.setPos(50,50);
                agent.setDir(1, 0);
                agent.setLeftOfPath(1100);
            }
        });
        Track tr = new Track(600,300);
        tr.loadFromFile("g2.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        AgentFitnessFunction fitnessFunction = new AgentFitnessFunction();

        fitnessFunction.setAgent(car);
        fitnessFunction.setGame(game);
        fitnessFunction.setTresHold(600);
        fitnessFunction.setTickLimit(2000);

        ChromosomeManager chromosomeManager = new ChromosomeManager(1000,car.getNumOfGens(),fitnessFunction);
        chromosomeManager.evolution(5);
        List<Chromosome> chromosomeList = fitnessFunction.getChromosomeList();
        tr.loadFromFile("track.map");
        //carEvolution.removeCrashed(chromosomeList,tr);

        showAll2(chromosomeList, tr);

    }
    public static void teachingNNCar(){
        NNCarFactory nnCarFactory = new NNCarFactory();
        //nnCarFactory.
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
    public static void showAll2(List<Chromosome> list,Track track){
        System.out.println(list.size());

        Game game = new Game();
        game.setTrack(track);

        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();

        for(Chromosome i:list){
            Car car = new Car();
            car.setMaxWheelsAngle(Math.PI / 3);
            car.setMaxSpeed(2);
            car.setLength(50);
            car.setWidth(3);
            //SimpleIntegerNeuralNetworkControl simpleNeuralNetworkControl = new SimpleIntegerNeuralNetworkControl();
            SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl(config,new ThActivationFunction());
            car.setCarControl(simpleNeuralNetworkControl);
            car.addSharp(new Sharp(5, 80, -Math.PI / 4));
            car.addSharp(new Sharp(5,80,0));
            car.addSharp(new Sharp(5,80,+Math.PI/4));
            car.setLeftOfPath(200000);
            car.setPos(50,200);
            car.setDir(0, 1);
            double gens[] = new double[i.getGens().length];

            System.out.print(car.getId() + "[ ");
            for(int j = 0;j<gens.length;j++){
                gens[j] = (int)(i.getGens()[j]*100);
                System.out.print(gens[j] + " ");
            }
            System.out.println("]");

            simpleNeuralNetworkControl.setGens(gens);

            game.addCar(car);
            //System.out.println(car.getId() + " " + i.toString());
        }
        game.startRealTimeSimulation(10000);
        game.waitEnd();
    }

}