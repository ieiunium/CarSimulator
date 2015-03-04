package com.kirill.simulator.core.agents.controls.simpletank;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.interfaces.Chromosomal;
import com.kirill.simulator.core.math.genetics.Chromosome;
import com.kirill.simulator.core.math.neural.NeuralNetwork;
import com.kirill.simulator.core.math.neural.functions.ActivationFunction;
import com.kirill.simulator.core.sensors.Sharp;

/**
 * Created by kirill-good on 28.2.15.
 */
public class SimpleTankNNControl implements SimpleTankControl,Chromosomal {
    private SimpleTank simpleTank;
    private NeuralNetwork nn;
    private double in[],out[];

    public SimpleTankNNControl(int config[],ActivationFunction activationFunction) {
        in = new double[config[0]];
        out = new double[config[config.length-1]];
        nn = new NeuralNetwork(config,activationFunction);
    }

    @Override
    public void setTank(SimpleTank simpleTank) {
        this.simpleTank = simpleTank;

    }


    @Override
    public void tick() {
        Sharp[] sharps = simpleTank.getSharpManager().getSharps();
        for(int i = 0;i<in.length;i++) {
            in[i] = sharps[i].getValue();
        }
        out = nn.getOut(in);
        int max = 0;
        for(int i = 0;i<out.length;i++) {
            if(out[i] > out[max]){
                max = i;
            }
        }
        switch (max){
            case 0:
                simpleTank.setTankState(SimpleTank.TankState.FORWARD);
                break;
            case 1:
                simpleTank.setTankState(SimpleTank.TankState.BACKWARD);
                break;
            case 2:
                simpleTank.setTankState(SimpleTank.TankState.LEFTTURN);
                break;
            case 3:
                simpleTank.setTankState(SimpleTank.TankState.RIGHTTURN);
                break;
        }
    }

    @Override
    public void setChromosome(Chromosome chromosome) {
        nn.setGens(chromosome.getGens());
    }

    @Override
    public Chromosome getChromosome() {
        //Chromosome res = new Chromosome(nn.numOfGens());

        return null;
    }

    @Override
    public int getNumOfGens() {
        return this.nn.numOfGens();
    }
}
