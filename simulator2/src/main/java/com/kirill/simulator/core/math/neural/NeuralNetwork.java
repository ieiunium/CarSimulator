package com.kirill.simulator.core.math.neural;

import com.kirill.simulator.core.math.neural.functions.ActivationFunction;

/**
 * Created by kirill-good on 23.2.15.
 */
public class NeuralNetwork {
    protected NeuralLayer layer[];
    protected double []gens;

    public NeuralNetwork(int config[],ActivationFunction activationFunction){

        layer = new NeuralLayer[config.length-1];
        for(int i=0;i<config.length-1;i++){
            layer[i] = new NeuralLayer(config[i],config[i+1],activationFunction);
        }
        gens = new double[numOfGens()];
    }

    public int numOfGens(){

        int res = 0;
        for(NeuralLayer i: layer){
            for(Neuron j: i.neuron){
                res += j.w.length + 1;
            }
        }
        return res;
    }

    public double[] getOut(double x[]){

        double res[] = x;
        for(int i = 0;i<layer.length;i++){
            res = layer[i].getOut( res );
        }
        return res;
    }

    public double[] getGens(){

        int c = 0;
        for(NeuralLayer i: layer){
            for(Neuron j: i.neuron){
                gens[c++] = j.T;
                for(double k: j.w){
                    gens[c++] = k;
                }
            }
        }
        return gens;
    }

    public void setGens(double[] gens){

        if(gens.length==this.gens.length){
            int c = 0;
            for(NeuralLayer i: layer){
                for(Neuron j: i.neuron){
                    j.T = gens[c++];
                    for(int k=0;k<j.w.length;k++){
                        j.w[k] = gens[c++];
                    }
                }
            }
        }
    }

}
