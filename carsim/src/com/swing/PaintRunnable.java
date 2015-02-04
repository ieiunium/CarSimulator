package com.swing;

import com.sim.core.items.Car;
import com.sim.core.Game;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * Created by kirill-good on 4.2.15.
 */
public class PaintRunnable implements Runnable{
    private volatile Game game;
    private volatile List<Car> cars;
    private JFrame frame;
    private long millisPerTicks = 100;

    public PaintRunnable(Game game, JFrame frame) {
        this.game = game;
        this.frame = frame;
        cars = this.game.getCars();
    }

    @Override
    public void run() {
        while(true){
            //System.out.println("!");
            paint();
            try {
                Thread.sleep(millisPerTicks,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(){
        Graphics g = frame.getGraphics();
        g.clearRect(0,0,frame.getWidth(),frame.getHeight());
        for(Car car:cars){
            paintCar(g,car);
        }
    }
    public static void paintCar(Graphics g,Car car){
        int x1 = (int)car.getPos().getX();
        int y1 = (int)car.getPos().getY();
        int x2 = (int)car.getHeadX();
        int y2 = (int)car.getHeadY();
        g.drawLine(x1,y1,x2,y2);
    }
}
