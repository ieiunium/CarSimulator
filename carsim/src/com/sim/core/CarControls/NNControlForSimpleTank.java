package com.sim.core.CarControls;

import com.sim.core.Sensors.Sharp;
import com.sim.core.interfaces.CarControl;
import com.sim.core.items.Car;
import com.sim.core.math.neural.NeuralNetwork;
import com.sim.core.math.neural.integer.IntegerNeuralNetwork;

/**
 * Created by kirill-good on 23.2.15.
 */
public class NNControlForSimpleTank extends SimpleIntegerNeuralNetworkControl{
    protected IntegerNeuralNetwork nn;
    protected int in[];
    protected int out[];
    public int numOfGens(){
        return nn.numOfGens();
    }
    public int[] getIntGens(){
        return nn.getIntGens();
    }
    public void setGens(int[] gens){
        nn.setGens(gens);
    }
    public NNControlForSimpleTank(){

        int []config={3,4};
        nn = new IntegerNeuralNetwork(config);
        in = new int[3];
        out = new int[4];
    }
    @Override
    public void tick(Car car) {
        Sharp[] sharps = car.getSharps();
        in[0] = (int)sharps[0].getValue();
        in[1] = (int)sharps[1].getValue();
        in[2] = (int)sharps[2].getValue();
        out = nn.getOut(in);
        int res = 0;
        for(int i = 0;i<out.length;i++){
            if(out[res]>out[i]){
                res = i;
            }
        }
        car.setAction(res,0);
    }

    @Override
    public String toString() {
        return "SimpleIntegerNeuralNetworkControl{" +
                "nn=" + nn +
                '}';
    }
}
