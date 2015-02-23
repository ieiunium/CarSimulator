package com.sim.core.CarControls;

import com.sim.core.interfaces.CarControl;
import com.sim.core.items.Car;
import com.sim.core.items.SimpleTank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kirill-good on 23.2.15.
 */
public class SimpleTankConsoleControl implements CarControl,Runnable {
    @Override
    public void tick(Car car) {
        ((SimpleTank)car).setTankState(tankState);
    }
    public SimpleTankConsoleControl() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    private volatile SimpleTank.TankState tankState = SimpleTank.TankState.STOP;
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
}
