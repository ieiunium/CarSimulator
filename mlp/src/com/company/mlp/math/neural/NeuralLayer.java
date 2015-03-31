package com.company.mlp.math.neural;

import com.company.mlp.math.neural.functions.ActivationFunction;

/**
 * Created by kirill-good on 10.2.15.
 */
public class NeuralLayer {
    protected Neuron neuron[];
    protected double outs[];

    public NeuralLayer(int in,int out,ActivationFunction activationFunction){
        neuron = new Neuron[out];
        outs = new double[out];
        for(int i = 0;i<neuron.length;i++){
            neuron[i] = new Neuron(in,activationFunction);
            outs[i] = 0;
        }
    }
    double[] getOut(double x[]){
        for(int i = 0;i<neuron.length;i++){
            outs[i] = neuron[i].getOut(x);
        }
        return outs;
    }
    public void calcError(double a,double etalon[]){
        for (int i = 0; i < neuron.length; i++) {
            neuron[i].g = outs[i] - etalon[i];

            /*for (int j = 0; j < neuron[i].w.length; j++) {
                neuron[i].w[j] = neuron[i].w[j] - a * neuron[i].g * neuron[i].activationFunction.dF(neuron[i].out) * neuron[i].out;
                neuron[i].T = neuron[i].T + a * neuron[i].g * neuron[i].activationFunction.dF(neuron[i].out);
            }*/
        }
    }

    public void calcError(double a,NeuralLayer neuralLayer){
        for (int i = 0; i < neuron.length; i++) {
            neuron[i].g = 0;
            for (int j = 0; j < neuralLayer.neuron.length; j++) {
                neuron[i].g +=
                        neuralLayer.neuron[j].g *
                        neuralLayer.neuron[j].activationFunction.dF(neuralLayer.outs[j]) *
                        neuron[i].out;
            }
            /*
            for (int j = 0; j < neuron[i].w.length; j++) {
                neuron[i].w[j] = neuron[i].w[j] - a * neuron[i].g * neuron[i].activationFunction.dF(neuron[i].out) * neuron[i].out;
                neuron[i].T = neuron[i].T + a * neuron[i].g * neuron[i].activationFunction.dF(neuron[i].out);
            }*/
        }


    }
}
