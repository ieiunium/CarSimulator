package com.swing;

import com.sim.core.agents.car.Car;
import com.sim.core.interfaces.Agent;
import com.sim.simulation.Game;
import com.sim.simulation.Track;

import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * Created by kirill-good on 4.2.15.
 */
public class PaintRunnable implements Runnable{
    public static final int DX =0;
    public static final int DY =30;
    private volatile Game game;
    private volatile List<Agent> agents;
    private JFrame frame;
    private Track track;
    private long millisPerTicks = 20;

    public PaintRunnable(Game game, JFrame frame) {
        this.game = game;
        this.frame = frame;
        agents = this.game.getAgents();
        track = game.getTrack();
    }

    @Override
    public void run() {
        while(true){
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
        //g.clearRect(0,0,frame.getWidth(),frame.getHeight());
        for(int i = 0; i<track.getWidth();i++){
            for(int j = 0; j<track.getHeight();j++){
                if(track.getPix(i,j)){
                    g.setColor(Color.BLACK);
                }else{
                    g.setColor(Color.WHITE);
                }
                g.drawOval(i+DX,j+DY,1,1);
            }
        }
        for(Agent agent: agents){
            agent.paint(g, DX, DY);
        }
    }
}
