package com.sim.core.math.neural;

/**
 * Created by kirill-good on 10.2.15.
 */
public class NeuralLayer {
    protected Neuron neuron[];
    protected double outs[];

    public NeuralLayer(int in,int out){
        neuron = new Neuron[out];
        outs = new double[out];
        for(int i = 0;i<neuron.length;i++){
            neuron[i] = new Neuron(in);
            outs[i] = 0;
        }
    }
    double[] getOut(double x[]){
        for(int i = 0;i<neuron.length;i++){
            outs[i] = neuron[i].getOut(x);
        }
        return outs;
    }
}
