package com.kirill.simulator.core.math.genetics;

import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.interfaces.AgentControl;
import com.kirill.simulator.core.interfaces.Chromosomal;
import com.kirill.simulator.core.simulation.Game;
import com.kirill.simulator.view.swing.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 2.3.15.
 */
public class AgentFitnessFunction extends FitnessFunction {
    private Agent agent;
    private Game game;
    private Chromosomal chromosomal;
    private long tickLimit;
    private long saveDistance;
    private List<Chromosome> chromosomes= new ArrayList<Chromosome>();

    public AgentFitnessFunction(Agent agent, Game game, Chromosomal chromosomal, long tickLimit, long saveDistance) {
        this.agent = agent;
        this.game = game;
        this.chromosomal = chromosomal;
        this.tickLimit = tickLimit;
        this.saveDistance = saveDistance;
    }

    @Override
    public double fitness(Chromosome chromosome) {
        chromosomal.setChromosome(chromosome);
        agent.reset();
        double x1 = agent.getX();
        double y1 = agent.getY();
        game.startSimulation(tickLimit,0);
        game.waitEnd();
        double x2 = agent.getX();
        double y2 = agent.getY();
        double res = Math.hypot(x1 - x2, y1 - y2);
        if(res>saveDistance){
            System.out.println("\t" + res);
            chromosomes.add(chromosome.getCopy());
            agent.reset();
            GameView gameView = new GameView(game);
            gameView.startPaint();
            game.startSimulation(tickLimit,40);
            game.waitEnd();
            gameView.kill();
        }
        return res;
    }

    public List<Chromosome> getBest(){
        return chromosomes;
    }
}
