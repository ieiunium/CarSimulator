package com.sim.core.math.genetics;

import com.sim.core.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.items.Car;
import com.sim.core.items.Track;
import com.sim.simulation.Game;
import com.swing.GameSwingVideoAdapter;
import com.swing.SimpleCarControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill-good on 11.2.15.
 */
public class CarEvolution extends ChromosomeManager{

    public CarEvolution(Car car, Game game, int numberOfChromosome, SimpleNeuralNetworkControl simpleNeuralNetworkControl){
        super(numberOfChromosome, simpleNeuralNetworkControl.numOfGens());
        CarFitness fitnessFunction = new CarFitness();
        fitnessFunction.car = car;
        fitnessFunction.game = game;
        fitnessFunction.simpleNeuralNetworkControl = simpleNeuralNetworkControl;
        for(Chromosome i:chromosomes){
            i.setFitnessFunction(fitnessFunction);
        }
    }

    public void evolution(int steps){

        List<Chromosome> list = new ArrayList<Chromosome>();


        Chromosome children[] = chromosomes.clone();
        for(int step = 0; step < steps; step++) {

            for(Chromosome i: chromosomes){
                i.calcFitness();
                if(i.fitness()>450){
                    list.add(i.getCopy());
                }
            }
            Arrays.sort(chromosomes);
            int half = chromosomes.length / 2;
            for (int i = 0; i < half; i++) {
                //System.out.println(chromosomes[i].fitness());
                int i1 = Chromosome.random.nextInt(half);
                int i2;
                do{
                    i2 = Chromosome.random.nextInt(half);
                }while (i1==i2);
                Chromosome d1 = chromosomes[i1].getCopy();
                Chromosome d2 = chromosomes[i2].getCopy();
                Chromosome.crossOver(d1, d2);
                children[i * 2] = d1;
                children[i * 2 + 1] = d2;
            }
            Chromosome tmp[] = children;
            children = chromosomes;
            chromosomes = tmp;
        }

    }
    public void test1(List<Chromosome> list){
        Car car = new Car();
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(50);
        car.setWidth(3);
        SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
        car.setCarControl(simpleNeuralNetworkControl);
        car.addSharp(new Sharp(5, 110, -Math.PI / 4));
        car.addSharp(new Sharp(5,110,0));
        car.addSharp(new Sharp(5,110,+Math.PI/4));
        Track tr = new Track(600,300);
        tr.loadFromFile("track.map");
        Game game = new Game();
        game.setTrack(tr);
        game.addCar(car);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();

        for(Chromosome i:list){
            car.setLeftOfPath(200000);
            car.setPos(50,50);
            car.setDir(1, 1);
            simpleNeuralNetworkControl.setGens(i.gens);
            game.startRealTimeSimulation(1500);
            game.waitEnd();
        }
    }

    public void test2(List<Chromosome> list){
/*
        Track tr = new Track(600,300);
        tr.loadFromFile("track.map");
        Game game = new Game();
        game.setTrack(tr);

        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();

        for(Chromosome i:list){
            Car car = new Car();
            car.setMaxWheelsAngle(Math.PI / 3);
            car.setMaxSpeed(2);
            car.setLength(50);
            car.setWidth(3);
            SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
            car.setCarControl(simpleNeuralNetworkControl);
            car.addSharp(new Sharp(5, 110, -Math.PI / 4));
            car.addSharp(new Sharp(5,110,0));
            car.addSharp(new Sharp(5,110,+Math.PI/4));
            car.setLeftOfPath(200000);
            car.setPos(50,50);
            car.setDir(1, 1);
            simpleNeuralNetworkControl.setGens(i.gens);
            game.addCar(car);
            game.startRealTimeSimulation(1500);
            game.waitEnd();
        }*/
    }
}

class CarFitness extends FitnessFunction{
    volatile Car car;
    volatile Game game;
    SimpleNeuralNetworkControl simpleNeuralNetworkControl;
    public double fitness(Chromosome chromosome){
        simpleNeuralNetworkControl.setGens(chromosome.gens);
        car.setPos(50,50);
        car.setDir(1, 0);
        car.setLeftOfPath(530);
        car.setSpeed(0);
        car.setWheelsAngle(0);
        double x = car.getPos().getX();
        double y = car.getPos().getY();
        game.startSimulation(2000);
        game.waitEnd();
        double x1 = car.getPos().getX();
        double y1 = car.getPos().getY();
        //System.out.println(Math.hypot(x1-x,y1-y));
        return Math.hypot(x1-x,y1-y);
    }
}