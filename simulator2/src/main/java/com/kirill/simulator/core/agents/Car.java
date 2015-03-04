package com.kirill.simulator.core.agents;

import com.kirill.simulator.core.agents.controls.car.CarControl;
import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankControl;
import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.interfaces.OnlyReadableTrack;
import com.kirill.simulator.core.interfaces.ResetFunction;
import com.kirill.simulator.core.math.Vector2f;
import com.kirill.simulator.core.math.genetics.Chromosome;
import com.kirill.simulator.core.sensors.Sharp;
import com.kirill.simulator.core.sensors.SharpManager;

import javax.media.opengl.GL2;
import java.awt.*;

/**
 * Created by kirill-good on 25.2.15.
 */
public class Car implements Agent{
    protected static int carId=0;
    protected final int id = carId++;
    protected Vector2f pos = new Vector2f(0,0);
    protected Vector2f dir = new Vector2f(1,0);
    protected int length=50;
    protected double width = 5;
    protected CarControl carControl;
    protected SharpManager sharpManager = new SharpManager();
    protected OnlyReadableTrack track = null;
    protected double leftOfPath = 100;
    protected ResetFunction resetFunction = new ResetFunction() {
        @Override
        public void reset(Agent agent) {

        }
    };
    protected int speed;
    protected double maxSpeed;
    protected int wheelsAngle=0;
    protected double maxWheelsAngle=Math.PI/3;
    public Car(CarControl carControl) {
        this.carControl = carControl;
        this.carControl.setCar(this);
        this.maxSpeed = 3;
    }
    @Override
    public void tick() {

    }

    @Override
    public void setTrack(OnlyReadableTrack onlyReadableTrack) {
        this.track = onlyReadableTrack;
    }

    @Override
    public boolean collision() {
        return false;
    }

    @Override
    public void paint(Graphics g, int dx, int dy) {

    }

    @Override
    public void setPos(double x, double y) {
        pos.setXY(x,y);
    }

    @Override
    public void setDir(double x, double y) {
        dir.setXY(x,y);
    }

    @Override
    public SharpManager getSharpManager() {
        return sharpManager;
    }

    @Override
    public void addSharp(Sharp sharp) {
        sharpManager.addSharp(sharp);
    }

    @Override
    public void glPaint(GL2 gl, int dX, int dY, int dZ) {

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

    @Override
    public double getLeftOfPath() {
        return leftOfPath;
    }

    @Override
    public void setLeftOfPath(double leftOfPath) {
        this.leftOfPath = leftOfPath;
    }

    public double getMaxWheelsAngle() {
        return maxWheelsAngle;
    }

    public void setMaxWheelsAngle(double maxWheelsAngle) {
        this.maxWheelsAngle = maxWheelsAngle;
    }

    public int getWheelsAngle() {
        return wheelsAngle;
    }

    public void setWheelsAngle(int wheelsAngle) {
        this.wheelsAngle = wheelsAngle;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
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
