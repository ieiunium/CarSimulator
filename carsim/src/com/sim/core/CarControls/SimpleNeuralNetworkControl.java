package com.sim.core.CarControls;

import com.sim.core.interfaces.CarControl;
import com.sim.core.items.Car;
import com.sim.core.Sensors.Sharp;
import com.sim.core.math.neural.NeuralNetwork;

/**
 * Created by kirill-good on 5.2.15.
 */
public class SimpleNeuralNetworkControl implements CarControl {
    private NeuralNetwork nn;
    private double in[];
    private double out[];
    public SimpleNeuralNetworkControl(){
        int []config={3,2};
        nn = new NeuralNetwork(config);
        in = new double[3];
        out = new double[2];
    }
    @Override
    public void tick(Car car) {
        Sharp[] sharps = car.getSharps();
        in[0] = sharps[0].getValue();
        in[1] = sharps[1].getValue();
        in[2] = sharps[2].getValue();
        out = nn.getOut(in);
        car.setWheelsAngle((int)(out[0]*100));
        car.setSpeed((int)(out[1]*100));
    }
}
