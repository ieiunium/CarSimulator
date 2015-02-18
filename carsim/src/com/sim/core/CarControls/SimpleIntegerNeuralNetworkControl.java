package com.sim.core.CarControls;

import com.sim.core.Sensors.Sharp;
import com.sim.core.interfaces.CarControl;
import com.sim.core.items.Car;
import com.sim.core.math.neural.NeuralNetwork;
import com.sim.core.math.neural.integer.IntegerNeuralNetwork;

/**
 * Created by kirill-good on 5.2.15.
 */
public class SimpleIntegerNeuralNetworkControl implements CarControl {
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
    public SimpleIntegerNeuralNetworkControl(){
        int []config={3,2};
        nn = new IntegerNeuralNetwork(config);
        in = new int[3];
        out = new int[2];
    }
    @Override
    public void tick(Car car) {
        Sharp[] sharps = car.getSharps();
        in[0] = (int)sharps[0].getValue();
        in[1] = (int)sharps[1].getValue();
        in[2] = (int)sharps[2].getValue();
        out = nn.getOut(in);
        car.setWheelsAngle((int)(out[0]));
        car.setSpeed((int)(out[1]));
    }
}
