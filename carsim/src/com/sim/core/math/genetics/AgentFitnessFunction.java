package com.sim.core.math.genetics;

import com.sim.core.interfaces.Agent;
import com.sim.core.simulation.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 6.3.15.
 */
public class AgentFitnessFunction extends FitnessFunction{
    protected Agent agent;
    protected Game game;
    protected List<Chromosome> chromosomeList = new ArrayList<Chromosome>();
    protected int tickLimit;
    protected int tresHold;

    public double fitness(Chromosome chromosome){
        agent.setChromosome(chromosome);
        agent.reset();
        double x = agent.getX();
        double y = agent.getY();
        game.startSimulation(tickLimit);
        game.waitEnd();
        double x1 = agent.getX();
        double y1 = agent.getY();
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Chromosome> getChromosomeList() {
        return chromosomeList;
    }

    public void setChromosomeList(List<Chromosome> chromosomeList) {
        this.chromosomeList = chromosomeList;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}