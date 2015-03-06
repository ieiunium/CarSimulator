package com.sim.core.math.genetics;

import com.sim.core.agents.car.Car;
import com.sim.core.agents.car.SimpleNeuralNetworkControl;
import com.sim.core.simulation.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 6.3.15.
 */
public class AgentFitnessFunction extends FitnessFunction{
    protected Car car;
    protected Game game;
    protected List<Chromosome> chromosomeList = new ArrayList<Chromosome>();
    protected SimpleNeuralNetworkControl simpleNeuralNetworkControl;
    protected int tickLimit;
    protected int tresHold;

    public double fitness(Chromosome chromosome){
        simpleNeuralNetworkControl.setGens(chromosome.gens);
        car.setPos(50,50);
        car.setDir(1, 0);
        car.setLeftOfPath(1100);
        car.setSpeed(0);
        car.setWheelsAngle(0);
        double x = car.getPos().getX();
        double y = car.getPos().getY();
        game.startSimulation(tickLimit);
        game.waitEnd();
        double x1 = car.getPos().getX();
        double y1 = car.getPos().getY();
        double res = Math.hypot(x1-x,y1-y);
        if(res>tresHold){
            chromosomeList.add(chromosome.getCopy());
        }
        //System.out.println(Math.hypot(x1-x,y1-y));
        return res;
    }
    public int getTickLimit() {
        return tickLimit;
    }

    public void setTickLimit(int tickLimit) {
        this.tickLimit = tickLimit;
    }

    public int getTresHold() {
        return tresHold;
    }

    public void setTresHold(int tresHold) {
        this.tresHold = tresHold;
    }
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public SimpleNeuralNetworkControl getSimpleNeuralNetworkControl() {
        return simpleNeuralNetworkControl;
    }

    public void setSimpleNeuralNetworkControl(SimpleNeuralNetworkControl simpleNeuralNetworkControl) {
        this.simpleNeuralNetworkControl = simpleNeuralNetworkControl;
    }

    public List<Chromosome> getChromosomeList() {
        return chromosomeList;
    }

    public void setChromosomeList(List<Chromosome> chromosomeList) {
        this.chromosomeList = chromosomeList;
    }
}