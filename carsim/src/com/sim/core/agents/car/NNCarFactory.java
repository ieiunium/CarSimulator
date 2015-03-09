package com.sim.core.agents.car;

import com.sim.core.Sensors.Sharp;
import com.sim.core.interfaces.Agent;
import com.sim.core.interfaces.AgentFactory;
import com.sim.core.interfaces.ResetFunction;
import com.sim.core.math.Vector2f;
import com.sim.core.math.neural.functions.ActivationFunction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 7.3.15.
 */
public class NNCarFactory implements AgentFactory{
    protected Vector2f pos = new Vector2f(0,0)
            , dir = new Vector2f(1,0);
    protected double maxWheelsAngle=Math.PI/3;
    protected int length=50;
    protected int width=10;
    protected double maxSpeed=5;
    protected double leftOfPath = 100;
    protected ResetFunction resetFunction;
    protected ActivationFunction activationFunction;
    protected int configNN[];
    protected List<Sharp> sharpList = new ArrayList<Sharp>();
    protected Color color = Color.BLACK;
    @Override
    public Agent getNewAgent() {
        Car car = new Car();
        car.setPos(pos.getX(),pos.getY());
        car.setDir(dir.getX(), dir.getY());
        car.setMaxWheelsAngle(maxWheelsAngle);
        car.setMaxSpeed(maxSpeed);
        car.setLength(length);
        car.setWidth(width);
        car.setLeftOfPath(leftOfPath);
        car.setResetFunction(resetFunction);
        car.setCarControl(new NNCarControl(configNN, activationFunction));
        car.setColor(color);
        car.sharpManager.addSharp(sharpList);
        return car;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    public void setPos(double x,double y) {
        this.pos.setXY(x,y);
    }
    public Vector2f getDir() {
        return dir;
    }
    public void setDir(Vector2f dir) {
        this.dir = dir;
    }
    public void setDir(double x,double y) {
        this.dir.setXY(x,y);
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

    public double getLeftOfPath() {
        return leftOfPath;
    }

    public void setLeftOfPath(double leftOfPath) {
        this.leftOfPath = leftOfPath;
    }

    public ResetFunction getResetFunction() {
        return resetFunction;
    }

    public void setResetFunction(ResetFunction resetFunction) {
        this.resetFunction = resetFunction;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public int[] getConfigNN() {
        return configNN;
    }

    public void setConfigNN(int[] configNN) {
        this.configNN = configNN;
    }


    public List<Sharp> getSharpList() {
        return sharpList;
    }

    public void setSharpList(List<Sharp> sharpList) {
        this.sharpList = sharpList;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
