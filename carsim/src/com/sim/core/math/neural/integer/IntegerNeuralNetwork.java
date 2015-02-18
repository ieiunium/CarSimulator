package com.sim.core.math.neural.integer;

import com.sim.core.math.neural.NeuralNetwork;

/**
 * Created by kirill-good on 10.2.15.
 */
public class IntegerNeuralNetwork extends NeuralNetwork {
    public static int MULTIPLIER = 10000;
    private IntegerNeuralLayer layer[];
    private int []gens;

    public IntegerNeuralNetwork(int config[]){
        super();
        layer = new IntegerNeuralLayer[config.length-1];
        for(int i=0;i<config.length-1;i++){
            layer[i] = new IntegerNeuralLayer(config[i],config[i+1]);
        }
        gens = new int[numOfGens()];
    }
    public int numOfGens(){
        int res = 0;
        for(IntegerNeuralLayer i: layer){
            for(IntegerNeuron j: i.neuron){
                res += j.w.length + 1;
            }
        }
        return res;
    }
    public int[] getOut(int x[]){
        int res[] = x;
        for(int i = 0;i<layer.length;i++){
            res = layer[i].getOut( res );
        }
        return res;
    }
    public int[] getIntGens(){
        int c = 0;
        for(IntegerNeuralLayer i: layer){
            for(IntegerNeuron j: i.neuron){
                gens[c++] = j.T;
                for(int k: j.w){
                    gens[c++] = k;
                }
            }
        }
        return gens;
    }
    public void setGens(int[] gens){
        if(gens.length==this.gens.length){
            int c = 0;
            for(IntegerNeuralLayer i: layer){
                for(IntegerNeuron j: i.neuron){
                    j.T = gens[c++];
                    for(int k=0;k<j.w.length;k++){
                        j.w[k] = gens[c++];
                    }
                }
            }
        }
    }
}

