package com.swing;

import com.sim.core.Game;

import javax.swing.*;

/**
 * Created by kirill-good on 4.2.15.
 */
public class GameSwingVideoAdapter {
    private volatile Game game;
    private JFrame mainFrame = new SimpleFrame();
    private Thread mainThread;
    private PaintRunnable paintRunnable;

    public GameSwingVideoAdapter(Game game){
        this.game = game;
        mainFrame.setSize(640,480);
        mainFrame.setVisible(true);
    }

    public void startPaint(){
        paintRunnable = new PaintRunnable(game,mainFrame);
        mainThread = new Thread(paintRunnable);
        mainThread.start();
    }

}
