package com.kirill.simulator.core.agents.controls.simpletank;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.math.neural.NeuralNetwork;
import com.kirill.simulator.core.math.neural.functions.ActivationFunction;
import com.kirill.simulator.core.sensors.Sharp;

/**
 * Created by kirill-good on 28.2.15.
 */
public class SimpleTankNNControl implements SimpleTankControl {
    private SimpleTank simpleTank;
    private NeuralNetwork nn;
    private double in[],out[];

    public SimpleTankNNControl() {
        int config[] = {3,4};
        in = new double[config[0]];
        out = new double[config[config.length-1]];
        nn = new NeuralNetwork(config,new ActivationFunction());
    }

    @Override
    public void setTank(SimpleTank simpleTank) {
        this.simpleTank = simpleTank;

    }


    @Override
    public void tick() {
        Sharp[] sharps = simpleTank.getSharpManager().getSharps();
        for(int i = 0;i<in.length;i++) {
            in[i] = sharps[i].getValue();
        }
        System.out.println();

        out = nn.getOut(in);
        int max = 0;
        for(int i = 0;i<out.length;i++) {
            if(out[i] > out[max]){
                max = i;
            }
        }
        switch (max){
            case 0:
                simpleTank.setTankState(SimpleTank.TankState.FORWARD);
                break;
            case 1:
                simpleTank.setTankState(SimpleTank.TankState.BACKWARD);
                break;
            case 2:
                simpleTank.setTankState(SimpleTank.TankState.LEFTTURN);
                break;
            case 3:
                simpleTank.setTankState(SimpleTank.TankState.RIGHTTURN);
                break;
        }
    }
}
