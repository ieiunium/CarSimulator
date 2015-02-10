package com.sim.core.math.neural;

/**
 * Created by kirill-good on 10.2.15.
 */
public class NeuralNetwork {
    private NeuralLayer layer[];

    public NeuralNetwork(int config[]){
        layer = new NeuralLayer[config.length-1];
        for(int i=0;i<config.length-1;i++){
            layer[i] = new NeuralLayer(config[i],config[i+1]);
        }
    }

    public double[] getOut(double x[]){
        double res[] = x;
        for(int i = 0;i<layer.length;i++){
            res = layer[i].getOut( res );
        }
        return res;
    }
}

