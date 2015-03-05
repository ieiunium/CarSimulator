package com.sim.core.agents;

import com.sim.core.interfaces.*;
import com.sim.core.CarControls.SimpleNeuralNetworkControl;
import com.sim.core.Sensors.Sharp;
import com.sim.core.Sensors.SharpManager;
import com.sim.core.math.Vector2f;
import com.sim.core.math.genetics.Chromosome;

import java.awt.*;

/**
 * Created by kirill-good on 3.2.15.
 */
public class Car implements Agent,Chromosomal{
    protected static int carId=0;
    protected int id = carId++;
    protected Vector2f pos = new Vector2f(0,0)
                   , dir = new Vector2f(1,0);
    protected int wheelsAngle=0;
    protected double maxWheelsAngle=Math.PI/3;
    protected int length=50;
    protected int width=10;
    protected int speed=0;
    protected double maxSpeed=5;
    protected CarControl carControl = new SimpleNeuralNetworkControl();;
    protected SharpManager sharpManager = new SharpManager();
    protected OnlyReadableTrack track = null;
    protected double leftOfPath = 100;
    protected ResetFunction resetFunction;
    public Car(){
        this.carControl.setCar(this);
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
        this.carControl.setCar(this);
        if(carControl==null) {
            this.carControl = new SimpleNeuralNetworkControl();
        }
    }
    public void tick(){
        sharpManager.tick(track,pos,dir,length);
        carControl.tick();
        double realSpeed = speed*maxSpeed/100.0;
        if(leftOfPath < Math.abs(realSpeed)){
            realSpeed = leftOfPath;
        }
        leftOfPath -= Math.abs(realSpeed);
        double k = 0.2;
        double skidding = (k + (1-k) * Math.exp(Math.abs(speed)/100.0));
        double R = ( length/Math.tan(Math.abs(wheelsAngle*maxWheelsAngle/100.0)) ) * skidding;
        //System.out.println(leftOfPath);
        if(!( Double.isNaN(R) || Double.isInfinite(R) ) ){

            dir.normalization();
            int sign = wheelsAngle>=0?-1:+1;
            double circleDirX = ( -dir.getY() * R * sign);
            double circleDirY = ( +dir.getX() * R * sign);

            double circleX = pos.getX() + circleDirX;
            double circleY = pos.getY() + circleDirY;
            /*for(double t=0;t<Math.PI;t+=0.1){
                g.drawOval((int)(circleX+R*Math.cos(t)),(int)(circleY+R*Math.sin(t)),1,1);
                g.drawOval((int)(circleX-R*Math.cos(t)),(int)(circleY-R*Math.sin(t)),1,1);
            }*/
            Vector2f tmp = new Vector2f(-circleDirX,-circleDirY);
            double turnAngle = (realSpeed)/R;
            tmp.turn(sign * turnAngle);
            pos.setX(circleX+tmp.getX());
            pos.setY(circleY+tmp.getY());
            dir.turn(sign * turnAngle);
        }else{
            pos.setX(pos.getX() + (realSpeed) * dir.getX());
            pos.setY(pos.getY() + (realSpeed) * dir.getY());
        }

    }
    public double getRealSpeed(int x){
        return x*maxSpeed/100.0;
    }
    public double getLeftOfPath() {
        return leftOfPath;
    }

    public void setLeftOfPath(double leftOfPath) {
        this.leftOfPath = leftOfPath;
    }

    public CarControl getCarControl() {
        return carControl;
    }

    public void setCarControl(CarControl carControl) {
        this.carControl = carControl;
        this.carControl.setCar(this);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(Math.abs(speed)>100) {
            this.speed = Integer.signum(speed) * 100;
        }else{
            this.speed = speed;
        }
    }
    public int getWheelsAngle() {
        return wheelsAngle;
    }
    public void setWheelsAngle(int wheelsAngle) {
        if(Math.abs(wheelsAngle)>100){
            this.wheelsAngle = Integer.signum(wheelsAngle) * 100;
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

    @Override
    public SharpManager getSharpManager() {
        return sharpManager;
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

    @Override
    public double getX() {
        return pos.getX();
    }

    @Override
    public double getY() {
        return pos.getY();
    }

    @Override
    public void reset() {
        resetFunction.reset(this);
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

    @Override
    public boolean collision() {
        int x1 = (int)getX();
        int y1 = (int)getY();
        int x2 = (int)getHeadX();
        int y2 = (int)getHeadY();
        return track.getPix(x1,y1) || track.getPix(x2,y2);
    }

    public int getId() {
        return id;
    }

    public void paint(Graphics g,int DX,int DY){
        int x1 = (int)this.getPos().getX() + DX;
        int y1 = (int)this.getPos().getY() + DY;
        int x2 = (int)this.getHeadX() + DX;
        int y2 = (int)this.getHeadY() + DY;
        g.drawString(String.valueOf(id),(x1+x2)/2,(y1+y2)/2);
        g.drawLine(x1,y1,x2,y2);
    }

    @Override
    public void setChromosome(Chromosome chromosome) {
        carControl.setChromosome(chromosome);
    }

    @Override
    public Chromosome getChromosome() {
        return carControl.getChromosome();
    }

    @Override
    public int getNumOfGens() {
        return carControl.getNumOfGens();
    }
}
