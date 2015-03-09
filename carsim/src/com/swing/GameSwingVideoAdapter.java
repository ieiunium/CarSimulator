package com.swing;

import com.sim.core.simulation.Game;

import javax.swing.*;

/**
 * Created by kirill-good on 4.2.15.
 */
public class GameSwingVideoAdapter {
    private volatile Game game;
    private JFrame mainFrame = new JFrame();
    private Thread mainThread;
    private PaintRunnable paintRunnable;

    public GameSwingVideoAdapter(Game game){
        this.game = game;
        mainFrame.setSize(game.getTrack().getWidth(),game.getTrack().getHeight()+paintRunnable.DY);

        mainFrame.setVisible(true);
    }

    public void startPaint(){
        paintRunnable = new PaintRunnable(game,mainFrame);
        mainThread = new Thread(paintRunnable);
        mainThread.start();
    }
    public void kill(){
        stop();
        mainFrame.dispose();
    }
    public void stop(){
        mainThread.stop();
    }
}
