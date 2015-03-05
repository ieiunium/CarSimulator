package com.sim.core.math.genetics;

import com.sim.core.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.agents.Car;
import com.sim.core.items.Track;
import com.sim.core.math.neural.integer.IntegerNeuralNetwork;
import com.sim.simulation.Game;
import com.swing.GameSwingVideoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill-good on 11.2.15.
 */
public class CarEvolution extends ChromosomeManager{
    private List<Chromosome> bestCarChromosome = new ArrayList<Chromosome>();
    private Car etalonCar;

    public CarEvolution(Car car, Game game, int numberOfChromosome, SimpleNeuralNetworkControl simpleNeuralNetworkControl){
        super(numberOfChromosome, simpleNeuralNetworkControl.numOfGens());
        CarFitness fitnessFunction = new CarFitness();
        this.etalonCar = car;
        fitnessFunction.car = car;
        fitnessFunction.game = game;
        fitnessFunction.simpleNeuralNetworkControl = simpleNeuralNetworkControl;
        for(Chromosome i:chromosomes){
            i.setFitnessFunction(fitnessFunction);
        }
    }

    public void evolution(int steps){
        Chromosome children[] = chromosomes.clone();
        for(int step = 0; step < steps; step++) {

            for(Chromosome i: chromosomes){
                i.calcFitness();
                if(i.fitness()>400){
                    bestCarChromosome.add(i.getCopy());
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
            /*List<Chromosome> listT = Arrays.asList(chromosomes);
            Track tr = new Track(600,300);
            tr.loadFromFile("track2.map");
            this.showAll(listT,tr);*/
        }

    }
    @Deprecated
    public void showOne(List<Chromosome> list,Track track){
        Car car = new Car();
        car.setMaxWheelsAngle(Math.PI / 3);
        car.setMaxSpeed(2);
        car.setLength(30);
        car.setWidth(3);
        SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
        car.setCarControl(simpleNeuralNetworkControl);
        car.addSharp(new Sharp(5, 80, -Math.PI / 4));
        car.addSharp(new Sharp(5,80,0));
        car.addSharp(new Sharp(5,80,+Math.PI/4));
        Game game = new Game();
        game.setTrack(track);
        game.addCar(car);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();

        for(Chromosome i:list){
            car.setLeftOfPath(200000);
            car.setPos(50,50);
            car.setDir(1, 0);
            simpleNeuralNetworkControl.setGens(i.gens);
            game.startRealTimeSimulation(200);
            game.waitEnd();
        }

    }

    public void showAll(List<Chromosome> list,Track track){
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
            SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
            car.setCarControl(simpleNeuralNetworkControl);
            car.addSharp(new Sharp(5, 80, -Math.PI / 4));
            car.addSharp(new Sharp(5,80,0));
            car.addSharp(new Sharp(5,80,+Math.PI/4));
            car.setLeftOfPath(200000);
            car.setPos(50,200);
            car.setDir(0, 1);
            simpleNeuralNetworkControl.setGens(i.gens);
            game.addCar(car);
            System.out.println(car.getId() + " " + i.toString());
        }
        game.startRealTimeSimulation(200000);
        game.waitEnd();
    }
    public void showAll2(List<Chromosome> list,Track track){
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
            SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
            car.setCarControl(simpleNeuralNetworkControl);
            car.addSharp(new Sharp(5, 80, -Math.PI / 4));
            car.addSharp(new Sharp(5,80,0));
            car.addSharp(new Sharp(5,80,+Math.PI/4));
            car.setLeftOfPath(200000);
            car.setPos(50,200);
            car.setDir(0, 1);
            double gens[] = new double[i.gens.length];

            System.out.print(car.getId() + "[ ");
            for(int j = 0;j<gens.length;j++){
                gens[j] = (int)(i.gens[j]* IntegerNeuralNetwork.MULTIPLIER);
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

    public void removeCrashed(List<Chromosome> list,Track track){
        System.out.println(list.size());
        Game game = new Game();
        game.setTrack(track);

        for(Chromosome i:list){
            Car car = new Car();
            car.setMaxWheelsAngle(Math.PI / 3);
            car.setMaxSpeed(2);
            car.setLength(50);
            car.setWidth(3);
            SimpleNeuralNetworkControl simpleNeuralNetworkControl = new SimpleNeuralNetworkControl();
            car.setCarControl(simpleNeuralNetworkControl);
            car.addSharp(new Sharp(5, 80, -Math.PI / 4));
            car.addSharp(new Sharp(5,80,0));
            car.addSharp(new Sharp(5,80,+Math.PI/4));
            car.setLeftOfPath(200000);
            car.setPos(50,50);
            car.setDir(1, 1);
            simpleNeuralNetworkControl.setGens(i.gens);
            game.addCar(car);

        }
        game.startSimulation(10000);
        game.waitEnd();
        List<Car> l = game.getCars();
        list.clear();
        for(Car i:l){
            if(i.collision()){

            }else{
                double p[] = ((SimpleNeuralNetworkControl) i.getCarControl()).getGens();
                Chromosome chromosome = new Chromosome(p.length);
                chromosome.gens = p;
                list.add(chromosome);
            }
        }

    }

    public List<Chromosome> getCopyOfBestCarChromosome() {
        List<Chromosome> res = new ArrayList<Chromosome>();
        for(Chromosome i:bestCarChromosome){
            res.add(i.getCopy());
        }
        return res;
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
        car.setLeftOfPath(1100);
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