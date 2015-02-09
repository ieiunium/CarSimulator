package com.swing;

import com.sim.core.items.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by kirill-good on 4.2.15.
 */
public class TrackEditor {
    public static final int DX =25;
    public static final int DY =30;
    private Track track;
    private int penSize=1;
    private JFileChooser jFileChooser = new JFileChooser();
    private JFrame frame = new JFrame(){
        @Override
        public void paint(Graphics g){
            super.paint(g);
            g.clearRect(0,0,this.getWidth(),this.getHeight());
            if( track!=null ){
                g.drawLine(DX, DY, DX + track.getWidth(), DY);
                g.drawLine(DX, DY, DX, DY + track.getHeight());
                g.drawLine(DX, DY + track.getHeight(), DX + track.getWidth(), DY + track.getHeight());
                g.drawLine(DX + track.getWidth(), DY, DX + track.getWidth(), DY + track.getHeight());
            }
            for(int i = 0; i<track.getWidth();i++){
                for(int j = 0; j<track.getHeight();j++){
                    if(track.getPix(i,j)){
                        g.drawOval(i+DX,j+DY,1,1);
                    }
                }
            }
        }
    };

    private MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            boolean color=true;
            if(mouseEvent.getButton()==MouseEvent.BUTTON3){
                color=false;
            }


            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            for (int i = 0; i < penSize && i + x - DX < track.getWidth(); i++) {
                for (int j = 0; j < penSize && j + y - DY < track.getHeight(); j++) {
                    track.setPix(x - DX + i, y - DY + j, color);
                }
            }
            frame.paint(frame.getGraphics());

        }
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }
        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    };
    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if(keyEvent.getKeyCode() == KeyEvent.VK_UP){
                penSize++;
                frame.setTitle("penSize "+penSize);
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
                penSize--;
                penSize = penSize<1?1:penSize;
                frame.setTitle("penSize "+penSize);
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_S){
                jFileChooser.showSaveDialog(frame);
                track.saveToFile(jFileChooser.getSelectedFile().getAbsolutePath());
            }
            if(keyEvent.getKeyCode() == KeyEvent.VK_O){
                jFileChooser.showOpenDialog(frame);
                track.loadFromFile(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    };
    public TrackEditor(Track track){
        if( track == null ){
            this.track = new Track(640,480);
        }
        this.track = track;
        frame.setSize(track.getWidth()+50,track.getHeight()+50);
        frame.setVisible(true);
        frame.addMouseListener(mouseListener);
        frame.addKeyListener(keyListener);
        frame.setResizable(false);
    }

    public Track getTrack() {
        return track;
    }
}
