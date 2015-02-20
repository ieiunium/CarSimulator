package com.sim.core.items;

import com.sim.core.interfaces.CarControl;
import com.sim.core.math.Vector2f;
import com.sim.core.math.genetics.CarEvolution;
import com.swing.SimpleCarControl;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by kirill-good on 20.2.15.
 */
public class Tank extends Car{
    protected int speedL=0;
    protected int speedR=0;
    @Override
    public void paint(Graphics g, int DX, int DY) {
        g.drawLine((int)this.getBackX()+DX,(int)this.getBackY()+DY,(int)this.getHeadX()+DX,(int)this.getHeadY()+DY);
    }
    @Override
    public void tick() {
        sharpManager.tick(track,pos,dir,length);
        carControl.tick(this);
        //System.out.println("L="+speedL+ " R="+speedR);
        if(speedL == speedR){
            pos.setXY( pos.getX() + this.getRealSpeed(speedL)*dir.getX()
                      ,pos.getY() + this.getRealSpeed(speedL)*dir.getY() );
            //System.out.println("!");
        }else {
            double R = Math.abs( ( width * (speedL + speedR) ) / ( 2 * (speedR - speedL) ) );
            //System.out.println(R);
            dir.normalization();
            int sign = Math.abs(speedL)<Math.abs(speedR)?-1:+1;
            double circleDirX = ( -dir.getY() * R * sign);
            double circleDirY = ( +dir.getX() * R * sign);
            double circleX = pos.getX() + circleDirX;
            double circleY = pos.getY() + circleDirY;
            Vector2f tmp = new Vector2f(-circleDirX,-circleDirY);
            sign *= Math.signum((speedL+speedR)/2);
            double turnAngle = sign * Math.abs( this.getRealSpeed(Math.max(Math.abs(speedL),Math.abs(speedR))) ) / (R + width/2);
            tmp.turn(turnAngle);
            pos.setX(circleX+tmp.getX());
            pos.setY(circleY+tmp.getY());
            dir.turn(turnAngle);
        }
        /*double realSpeed = speed*maxSpeed/100.0;
        if(leftOfPath < Math.abs(realSpeed)){
            realSpeed = leftOfPath;
        }*/
    }

    @Override
    public void setAction(int in1, int in2) {
        this.setSpeedL(in1);
        this.setSpeedR(in2);
    }

    public double getBackY() {
        return pos.getY()-dir.getY()*length/2;
    }

    public double getBackX() {
        return pos.getX()-dir.getX()*length/2;
    }

    @Override
    public double getHeadY() {
        return pos.getY()+dir.getY()*length/2;
    }

    @Override
    public double getHeadX() {
        return pos.getX()+dir.getX()*length/2;
    }

    public int getSpeedL() {
        return speedL;
    }

    public void setSpeedL(int speedL) {
        this.speedL = speedL;
    }

    public int getSpeedR() {
        return speedR;
    }

    public void setSpeedR(int speedR) {
        this.speedR = speedR;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(int speed) {

    }

    @Override
    public int getWheelsAngle() {
        return 0;
    }

    @Override
    public void setWheelsAngle(int wheelsAngle) {

    }

    @Override
    public double getMaxWheelsAngle() {
        return 0;
    }

    @Override
    public void setMaxWheelsAngle(double maxWheelsAngle) {

    }
}
