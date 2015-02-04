package com.sim.core.items;

import com.sim.core.items.CarControls.CarControl;
import com.sim.core.items.math.Vector2f;

/**
 * Created by kirill-good on 3.2.15.
 */
public class Car {
    private Vector2f pos
                   , dir;
    private int wheelsAngle;
    private double maxWheelsAngle;
    private int length,width;

    private int speed;
    private double maxSpeed;

    CarControl carControl;

    public Car(Vector2f pos
            , Vector2f dir
            , double maxWheelsAngle
            , double maxSpeed
            , int length
            , int width
            , CarControl carControl) {

        this.pos = pos;
        this.dir = dir;
        this.dir.normalization();
        this.maxWheelsAngle = maxWheelsAngle;
        this.maxSpeed=maxSpeed;
        this.length = length;
        this.width = width;
        this.carControl=carControl;
    }

    public void tick(){
        carControl.tick(this);
        double R = length/Math.tan(Math.abs(wheelsAngle*maxWheelsAngle/100.0));
        if(!( Double.isNaN(R) || Double.isInfinite(R) ) ){

            dir.normalization();
            int sign = wheelsAngle>=0?-1:+1;
            double circleDirX = ( -dir.getY() * R * sign);
            double circleDirY = ( +dir.getX() * R * sign);

            double circleX = pos.getX() + circleDirX;
            double circleY = pos.getY() + circleDirY;
            /*
            for(double t=0;t<Math.PI;t+=0.1){
                g.drawOval((int)(circleX+R*Math.cos(t)),(int)(circleY+R*Math.sin(t)),1,1);
                g.drawOval((int)(circleX-R*Math.cos(t)),(int)(circleY-R*Math.sin(t)),1,1);
            }*/
            Vector2f tmp = new Vector2f(-circleDirX,-circleDirY);
            double turnAngle = (speed*maxSpeed/100)/R;
            tmp.turn(sign * turnAngle);
            pos.setX(circleX+tmp.getX());
            pos.setY(circleY+tmp.getY());
            dir.turn(sign * turnAngle);
        }else{
            pos.setX(pos.getX() + (speed*maxSpeed/100) * dir.getX());
            pos.setY(pos.getY() + (speed*maxSpeed/100) * dir.getY());
        }

    }

    public CarControl getCarControl() {
        return carControl;
    }

    public void setCarControl(CarControl carControl) {
        this.carControl = carControl;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWheelsAngle() {
        return wheelsAngle;
    }

    public void setWheelsAngle(int wheelsAngle) {
        if(Math.abs(wheelsAngle)>100){
            this.wheelsAngle = 100;
        }else {
            this.wheelsAngle = wheelsAngle;
        }
    }

    public double getMaxWheelsAngle() {
        return maxWheelsAngle;
    }

    public void setMaxWheelsAngle(double maxWheelsAngle) {
        this.maxWheelsAngle = maxWheelsAngle;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(double x,double y) {
        this.pos.setX(x);
        this.pos.setY(y);
    }

    public Vector2f getDir() {
        return dir;
    }

    public void setDir(double x,double y) {
        this.dir.setX(x);
        this.dir.setY(y);
        this.dir.normalization();
    }
    public double getHeadX() {
        return pos.getX()+dir.getX()*length;
    }
    public double getHeadY() {
        return pos.getY()+dir.getY()*length;
    }
}
