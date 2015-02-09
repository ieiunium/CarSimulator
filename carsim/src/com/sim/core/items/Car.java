package com.sim.core.items;

import com.sim.core.interfaces.CarControl;
import com.sim.core.interfaces.OnlyReadableTrack;
import com.sim.core.items.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.items.Sensors.Sharp;
import com.sim.core.items.Sensors.SharpManager;
import com.sim.core.items.math.Vector2f;

/**
 * Created by kirill-good on 3.2.15.
 */
public class Car{
    private Vector2f pos = new Vector2f(0,0)
                   , dir = new Vector2f(1,0);
    private int wheelsAngle=0;
    private double maxWheelsAngle=Math.PI/3;
    private int length=50;
    private int width=10;
    private int speed=0;
    private double maxSpeed=5;
    private CarControl carControl = new SimpleNeuralNetworkControl();;
    private SharpManager sharpManager = new SharpManager();
    private OnlyReadableTrack track = null;
    public Car(){

    }
    public Car(Vector2f pos
            , Vector2f dir
            , double maxWheelsAngle
            , double maxSpeed
            , int length
            , int width
            , CarControl carControl) {
        if(pos!=null){
            this.pos = pos;
        }
        if(dir!=null){
            this.dir = dir;
        }
        this.dir.normalization();
        if(maxWheelsAngle>0){
            this.maxWheelsAngle = maxWheelsAngle;
        }
        if(maxSpeed>0) {
            this.maxSpeed = maxSpeed;
        }
        if(length>0) {
            this.length = length;
        }
        if(width>0) {
            this.width = width;
        }
        this.carControl=carControl;
        if(carControl==null) {
            this.carControl = new SimpleNeuralNetworkControl();
        }
    }

    public void tick(){
        sharpManager.tick(track,pos,dir,length);
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
    public void addSharp(Sharp sharp){
        sharpManager.addSharp(sharp);
    }
    public void removeSharp(Sharp sharp){
        sharpManager.removeSharp(sharp);
    }
    public Sharp[] getSharps(){
        return sharpManager.getSharps();
    }
    public OnlyReadableTrack getTrack() {
        return track;
    }

    public void setTrack(OnlyReadableTrack track) {
        this.track = track;
    }
}
