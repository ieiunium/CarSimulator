package com.kirill.simulator.view.jogl;

/**
 * Created by kirill-good on 25.2.15.
 */
import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.simulation.Game;
import com.kirill.simulator.core.simulation.Track;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.media.opengl.awt.GLCanvas;

public class JavaDia implements Runnable, KeyListener {
    private Thread displayT = new Thread(this);
    private boolean bQuit = false;
    private JavaRenderer javaRenderer;
    private Game game;
    private volatile List<Agent> agents;
    private Track track;

    public JavaDia(Game game) {
        this.game = game;
        this.track = game.getTrack();
        this.agents = game.getAgents();
    }

    public void start() {
        displayT.start();
    }

    public void run() {
        Frame frame = new Frame("Jogl 3D Shape/Rotation");
        GLCanvas canvas = new GLCanvas();
        int size = frame.getExtendedState();
        javaRenderer = new JavaRenderer(track,agents);
        canvas.addGLEventListener(javaRenderer);
        frame.add(canvas);
        frame.setUndecorated(true);
        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);
        canvas.addKeyListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bQuit = true;
            }
        });

        frame.setVisible(true);
        canvas.requestFocus();
        while( !bQuit ) {
            canvas.display();
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            displayT.stop();
            bQuit = true;
            System.exit(0);
        }else if(e.getKeyCode() == KeyEvent.VK_W){
            javaRenderer.dY += -10;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            javaRenderer.dX += +10;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            javaRenderer.dY += +10;
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            javaRenderer.dX += -10;
        }else if(e.getKeyCode() == KeyEvent.VK_Q){
            javaRenderer.dZ += -50;
        }else if(e.getKeyCode() == KeyEvent.VK_E){
            javaRenderer.dZ += +50;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}