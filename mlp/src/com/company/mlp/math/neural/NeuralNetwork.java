package com.company.mlp.math.neural;

import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.functions.ActivationFunction;

/**
 * Created by kirill-good on 10.2.15.
 */
public class NeuralNetwork {
    private NeuralLayer layer[];
    private double []gens;
    protected NeuralNetwork(){}
    public NeuralNetwork(int config[],ActivationFunction activationFunction){
        layer = new NeuralLayer[config.length-1];
        for(int i=0;i<config.length-1;i++){
            layer[i] = new NeuralLayer(config[i],config[i+1],activationFunction);
        }
        gens = new double[numOfGens()];
    }

    public void bpl(double in[][],double out[][],final double E,final double a){
        final ActivationFunction activationFunction = layer[0].neuron[0].activationFunction;
        double E0=1;
        if(in.length!=out.length){
            throw new RuntimeException("in.length!=out.length");
        }
        double g[][] = new double[layer.length][];
        for (int i = 0; i < layer.length; i++) {
            g[i] = new double[layer[i].neuron.length];
        }
        NeuralLayer lastLayer = layer[layer.length-1];
        while (true){
            E0 = 0;
            for (int i = 0; i < in.length; i++) {
                double curOut[] = this.getOut(in[i]);
                double Ed = FitnessFunction.getE(out[i],curOut);
                E0+=Ed;

                lastLayer.calcError(a,out[i]);
                for (int j = layer.length-2; j >=0 ; j--) {
                    layer[j].calcError(a,layer[j+1]);
                }
                double y[] = in[i];
                for (int j = 0; j < layer.length; j++) {
                    for (int k = 0; k < layer[j].neuron.length; k++) {
                        for (int l = 0; l < layer[j].neuron[k].w.length; l++) {
                            layer[j].neuron[k].w[l] -= a * layer[j].neuron[k].g * layer[j].neuron[k].activationFunction.dF(layer[j].neuron[k].out) * y[l];
                        }
                        layer[j].neuron[k].T += a * layer[j].neuron[k].g * layer[j].neuron[k].activationFunction.dF(layer[j].neuron[k].out);
                    }
                    y = layer[j].outs;
                }
            }
            System.out.println(E0);
            if(E0 < E){
                break;
            }
            //System.exit(0);
        }
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
        }else{
            throw new RuntimeException("bad gen's length");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NN{");
        for(NeuralLayer i: layer){
            sb.append("L{");
            for(Neuron j: i.neuron){
                sb.append("N{");
                sb.append("T=");
                sb.append(j.T*10000);
                sb.append(" ");
                sb.append("w[]={");
                for(int k=0;k<j.w.length;k++){
                    sb.append((int)(j.w[k]*10000));
                    sb.append(" ");
                }
                sb.append("}} ");
            }
            sb.append("} ");
        }
        sb.append("}+{ ");
        double g[] = getGens();
        for(int i=0;i<g.length;i++){
            sb.append(g[i]);
            sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}

