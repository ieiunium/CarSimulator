package com.sim.core.agents.car;

import com.sim.core.Sensors.Sharp;
import com.sim.core.math.genetics.Chromosome;
import com.sim.core.math.neural.NeuralNetwork;
import com.sim.core.math.neural.functions.ActivationFunction;


/**
 * Created by kirill-good on 5.2.15.
 */
public class SimpleNeuralNetworkControl implements CarControl {
    protected Car car;
    protected NeuralNetwork nn;
    protected double in[];
    protected double out[];
    public int numOfGens(){
        return nn.numOfGens();
    }
    public double[] getGens(){
        return nn.getGens();
    }
    public void setGens(double[] gens){
        nn.setGens(gens);
    }
    public SimpleNeuralNetworkControl(int config[],ActivationFunction activationFunction){

        nn = new NeuralNetwork(config,activationFunction);
        in = new double[3];
        out = new double[2];
    }
    @Override
    public void tick() {
        Sharp[] sharps = car.getSharpManager().getSharps();
        in[0] = sharps[0].getValue();
        in[1] = sharps[1].getValue();
        in[2] = sharps[2].getValue();
        out = nn.getOut(in);
        car.setSpeed( (int)(out[0]*100) );
        car.setWheelsAngle((int) (out[1] * 100));
    }

    @Override
    public String toString() {
        return "SimpleNeuralNetworkControl{" +
                "nn=" + nn +
                '}';
    }

    @Override
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public void setChromosome(Chromosome chromosome) {
        nn.setGens(chromosome.getGens());
    }

    @Override
    public Chromosome getChromosome() {
        return new Chromosome(nn.getGens().clone());
    }

    @Override
    public int getNumOfGens() {
        return nn.numOfGens();
    }
}
