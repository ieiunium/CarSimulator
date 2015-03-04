package com.kirill.simulator.core.agents.controls.simpletank;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.math.genetics.Chromosome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kirill-good on 25.2.15.
 */
public class SimpleTankConsoleControl implements SimpleTankControl,Runnable {
    private SimpleTank simpleTank;
    private Thread thread;
    private volatile SimpleTank.TankState tankState = SimpleTank.TankState.STOP;

    public SimpleTankConsoleControl() {
        startControl();
    }
    public void startControl(){
        thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }
    public void stopControl(){
        thread.stop();
    }
    @Override
    public void tick() {
        simpleTank.setTankState(tankState);
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.print(">>");
            try {
                String s = bufferedReader.readLine();
                if      (s.equals("R")||s.equals("r")){

                    tankState = SimpleTank.TankState.RIGHTTURN;
                    System.out.println("set right");
                }else if(s.equals("L")||s.equals("l")){

                    tankState = SimpleTank.TankState.LEFTTURN;
                    System.out.println("set left");
                }else if(s.equals("F")||s.equals("f")){

                    tankState = SimpleTank.TankState.FORWARD;
                    System.out.println("set forward");
                }else if(s.equals("B")||s.equals("b")){

                    tankState = SimpleTank.TankState.BACKWARD;
                    System.out.println("set backward");
                }else if(s.equals("S")||s.equals("s")){

                    tankState = SimpleTank.TankState.STOP;
                    System.out.println("set stop");
                }else{

                    System.out.println("bad line");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SimpleTank getTank() {
        return simpleTank;
    }

    public void setTank(SimpleTank simpleTank) {
        this.simpleTank = simpleTank;
    }

    @Override
    public void setChromosome(Chromosome chromosome) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chromosome getChromosome() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getNumOfGens() {
        throw new UnsupportedOperationException();
    }
}
