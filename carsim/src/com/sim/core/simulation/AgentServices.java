package com.sim.core.simulation;

import com.sim.core.agents.car.Car;
import com.sim.core.agents.car.NNCarFactory;
import com.sim.core.agents.tank.Tank;
import com.sim.core.interfaces.Agent;
import com.sim.core.interfaces.AgentFactory;
import com.sim.core.math.genetics.AgentFitnessFunction;
import com.sim.core.math.genetics.Chromosome;
import com.sim.core.math.genetics.ChromosomeManager;
import com.swing.GameSwingVideoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kirill on 18.3.15.
 */
public class AgentServices {
    public static List<Agent> teachAgents(int trashHold,int numOfAgents,int steps,AgentFactory agentFactory,Track track){
        Game game = new Game();
        game.setTrack(track);
        Agent agent = agentFactory.getNewAgent();
        AgentFitnessFunction fitnessFunction = new AgentFitnessFunction();
        fitnessFunction.setAgent(agent);
        fitnessFunction.setGame(game);
        fitnessFunction.setTresHold(trashHold);
        fitnessFunction.setTickLimit(1000);
        game.addAgent(agent);
        ChromosomeManager chromosomeManager = new ChromosomeManager(numOfAgents,agent.getNumOfGens(),fitnessFunction);
        //chromosomeManager.evolution(steps);
        chromosomeManager.mutationOnly(steps);
        //List<Chromosome> chromosomes = fitnessFunction.getChromosomeList();
        List<Chromosome> chromosomes = new ArrayList<Chromosome>();
        Collections.addAll(chromosomes,chromosomeManager.getChromosomes());
        List<Agent> agents = new ArrayList<Agent>();
        for(Chromosome i:chromosomes) {
            Agent a = agentFactory.getNewAgent();
            a.setChromosome(i);
            agents.add(a);
        }
        return agents;
    }
    public static void runAgents(int tickLimit,List<Agent> agents,Track track){
        Game game = new Game();
        game.setTrack(track);
        GameSwingVideoAdapter adapter = new GameSwingVideoAdapter(game);
        adapter.startPaint();
        for(Agent i:agents){
            i.reset();
            game.addAgent(i);
            i.setLeftOfPath(Integer.MAX_VALUE);
        }
        game.startRealTimeSimulation(tickLimit);
        game.waitEnd();
        adapter.kill();
    }
    public static void removeCrashedAgents(List<Agent> agents,Track track){
        Game game = new Game();
        game.setTrack(track);
        for(Agent i:agents){
            i.reset();
            game.addAgent(i);
            i.setLeftOfPath(Integer.MAX_VALUE);
        }
        game.startSimulation(10000);
        game.waitEnd();
        for (int i = agents.size()-1; i >= 0; i--) {
            if(agents.get(i).collision()){
                agents.remove(i);
            }
        }
    }
    public static List<Agent> agentBuilder(List<Chromosome> list,NNCarFactory nnCarFactory){
        List<Agent> res = new ArrayList<Agent>();
        for(Chromosome i:list){
            Agent car = nnCarFactory.getNewAgent();
            car.setChromosome(i.getCopy());
            res.add(car);
        }
        return res;
    }

    public static void showAllAgents(List<Chromosome> list,Track track, NNCarFactory nnCarFactory){
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
