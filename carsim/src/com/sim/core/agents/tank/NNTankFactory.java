package com.sim.core.agents.tank;

import com.sim.core.Sensors.Sharp;
import com.sim.core.interfaces.Agent;
import com.sim.core.interfaces.AgentFactory;
import com.sim.core.interfaces.ResetFunction;
import com.sim.core.math.Vector2f;
import com.sim.core.math.neural.functions.ActivationFunction;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by kirill-good on 9.3.15.
 */
public class NNTankFactory implements AgentFactory {
    protected double posx,posy,dirx,diry;
    protected int length=50;
    protected int width=10;
    protected double maxSpeed=5;
    protected double angleTurn=5;
    protected double leftOfPath = 100;
    protected ResetFunction resetFunction;
    protected ActivationFunction activationFunction;
    protected int configNN[];
    protected java.util.List<Sharp> sharpList = new ArrayList<Sharp>();
    protected Color color = Color.BLACK;
    @Override
    public Agent getNewAgent() {
        Tank tank = new Tank();
        tank.setMaxSpeed(maxSpeed);
        tank.setTurnAngle(angleTurn);
        tank.setWidth(width);
        tank.setLength(length);
        tank.setTankState(TankState.STOP);
        tank.setPos(posx,posy);
        tank.setDir(dirx,diry);
        NNTankConrtol nnTankConrtol = new NNTankConrtol(configNN,new ActivationFunction());
        tank.setTankControl(nnTankConrtol);
        tank.getSharpManager().addSharp(sharpList);
        tank.setColor(color);
        tank.setResetFunction(resetFunction);
        tank.setLeftOfPath(leftOfPath);
        return tank;
    }

    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public double getDirx() {
        return dirx;
    }

    public void setDirx(double dirx) {
        this.dirx = dirx;
    }

    public double getDiry() {
        return diry;
    }

    public void setDiry(double diry) {
        this.diry = diry;
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

    public double getAngleTurn() {
        return angleTurn;
    }

    public void setAngleTurn(double angleTurn) {
        this.angleTurn = angleTurn;
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
