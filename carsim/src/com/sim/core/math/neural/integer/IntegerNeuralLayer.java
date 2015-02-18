package com.sim.core.math.neural.integer;

/**
 * Created by kirill-good on 10.2.15.
 */
public class IntegerNeuralLayer {
    protected IntegerNeuron neuron[];
    protected int outs[];

    public IntegerNeuralLayer(int in, int out){
        neuron = new IntegerNeuron[out];
        outs = new int[out];
        for(int i = 0;i<neuron.length;i++){
            neuron[i] = new IntegerNeuron(in);
            outs[i] = 0;
        }
    }
    int[] getOut(int x[]){
        for(int i = 0;i<neuron.length;i++){
            outs[i] = neuron[i].getOut(x) / (IntegerNeuralNetwork.MULTIPLIER / 100);
        }
        return outs;
    }
}
