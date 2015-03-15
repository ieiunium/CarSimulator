package com.sim.core.agents.tank;

import com.sim.core.Sensors.Sharp;
import com.sim.core.math.genetics.Chromosome;
import com.sim.core.math.neural.NeuralNetwork;
import com.sim.core.math.neural.functions.ActivationFunction;

/**
 * Created by kirill-good on 9.3.15.
 */
public class NNTankConrtol implements TankControl{
    protected Tank tank;
    protected NeuralNetwork nn;
    protected double in[];
    protected double out[];

    public NNTankConrtol(int config[], ActivationFunction activationFunction) {
        nn = new NeuralNetwork(config,activationFunction);
        in = new double[config[0]];
        out = new double[config[config.length-1]];
    }

    @Override
    public void setTank(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void tick() {
        Sharp[] sharps = tank.getSharpManager().getSharps();
        for(int i = 0;i<sharps.length;i++){
            in[i] = sharps[i].getValue();
        }
        out = nn.getOut(in);
        int max = 0;
        for(int i = 0;i<out.length;i++){
            if(out[max]<out[i]){
                max = i;
            }
        }
        switch (max){
            case 0:
                tank.setTankState(TankState.FORWARD);

                break;
            case 1:
                tank.setTankState(TankState.BACKWARD);
                break;
            case 2:
                tank.setTankState(TankState.TURNLEFT);
                break;
            case 3:
                tank.setTankState(TankState.TURNRIGHT);
                break;
            default:
                throw new RuntimeException("bad tankState");
        }
    }

    @Override
    public void setChromosome(Chromosome chromosome) {
        nn.setGens(chromosome.getGens());
    }

    @Override
    public Chromosome getChromosome() {
        return new Chromosome(nn.getGens());
    }

    @Override
    public int getNumOfGens() {
        return nn.numOfGens();
    }

    @Override
    public String toString() {
        return nn.toString();
    }
}
