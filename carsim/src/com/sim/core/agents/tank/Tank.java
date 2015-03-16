package com.sim.core.agents.tank;

import com.sim.core.Sensors.Sharp;
import com.sim.core.Sensors.SharpManager;
import com.sim.core.interfaces.Agent;
import com.sim.core.interfaces.OnlyReadableTrack;
import com.sim.core.interfaces.ResetFunction;
import com.sim.core.math.Vector2f;
import com.sim.core.math.genetics.Chromosome;

import java.awt.*;

/**
 * Created by kirill-good on 9.3.15.
 */

public class Tank implements Agent {
    protected static int carId=0;
    protected int id = carId++;
    protected Vector2f pos = new Vector2f(0,0);
    protected Vector2f dir = new Vector2f(1,0);
    protected double maxSpeed=5;
    protected double turnAngle=Math.PI/60;
    protected int length=50;
    protected int width=10;
    protected TankControl tankControl;
    protected SharpManager sharpManager = new SharpManager();
    protected OnlyReadableTrack track = null;
    protected double leftOfPath = 100;
    protected ResetFunction resetFunction = new ResetFunction() {
        @Override
        public void reset(Agent agent) {

        }
    };
    protected Color color = Color.black;
    protected TankState tankState = TankState.STOP;

    @Override
    public void tick() {
        if(leftOfPath<0){
            return;
        }
        sharpManager.tick(track,pos,dir,length);
        tankControl.tick();
        double x;
        double y;
        switch (tankState){
            case STOP:
                break;
            case FORWARD:
                x = pos.getX() + dir.getX() * maxSpeed;
                y = pos.getY() + dir.getY() * maxSpeed;
                pos.setXY(x,y);
                leftOfPath -= maxSpeed;
            break;
            case BACKWARD:
                x = pos.getX() + dir.getX() * maxSpeed;
                y = pos.getY() + dir.getY() * maxSpeed;
                pos.setXY(x,y);
                leftOfPath -= maxSpeed;
            break;
            case TURNLEFT:
                dir.turn( +turnAngle );
                dir.normalization();
            break;
            case TURNRIGHT:
                dir.turn( -turnAngle );
                dir.normalization();
            break;
            default:
                throw new RuntimeException("bad tankState");
        }
    }

    @Override
    public String toString() {

        return "{"+this.id+"; " + tankControl.toString() + " ]";
    }

    @Override
    public void setTrack(OnlyReadableTrack onlyReadableTrack) {
        this.track = onlyReadableTrack;
    }

    @Override
    public boolean collision() {
        int x1 = (int)(this.pos.getX() + this.dir.getX() * length/2);
        int y1 = (int)(this.pos.getY() + this.dir.getY() * length/2);
        int x2 = (int)(this.pos.getX() - this.dir.getX() * length/2);
        int y2 = (int)(this.pos.getY() - this.dir.getY() * length/2);
        return track.getPix(x1,y1) || track.getPix(x2,y2);
    }

    @Override
    public void paint(Graphics g, int dx, int dy) {
        g.setColor(color);
        int x1 = dx+(int)(this.pos.getX() + this.dir.getX() * length*0.5);
        int y1 = dy+(int)(this.pos.getY() + this.dir.getY() * length*0.5);
        int x2 = dx+(int)(this.pos.getX() - this.dir.getX() * length*0.5);
        int y2 = dy+(int)(this.pos.getY() - this.dir.getY() * length*0.5);
        g.drawString(String.valueOf(id),dx + (x1+x2)/2,dy + (y1+y2)/2);
        g.drawLine(x1,y1,x2,y2);
    }

    @Override
    public void setPos(double x, double y) {
        this.pos.setXY(x,y);
    }

    @Override
    public void setDir(double x, double y) {
        this.dir.setXY(x,y);
        this.dir.normalization();
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
    public double getX() {
        return this.pos.getX();
    }

    @Override
    public double getY() {
        return this.pos.getY();
    }

    @Override
    public void reset() {
        this.resetFunction.reset(this);
    }

    @Override
    public void setResetFunction(ResetFunction resetFunction) {
        this.resetFunction = resetFunction;
    }

    @Override
    public double getLeftOfPath() {
        return leftOfPath;
    }

    @Override
    public void setLeftOfPath(double leftOfPath) {
        this.leftOfPath = leftOfPath;
    }

    @Override
    public void setChromosome(Chromosome chromosome) {
        tankControl.setChromosome(chromosome);
    }

    @Override
    public Chromosome getChromosome() {
        return tankControl.getChromosome();
    }

    @Override
    public int getNumOfGens() {
        return tankControl.getNumOfGens();
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getTurnAngle() {
        return turnAngle;
    }

    public void setTurnAngle(double turnAngle) {
        this.turnAngle = turnAngle;
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

    public TankControl getTankControl() {
        return tankControl;
    }

    public void setTankControl(TankControl tankControl) {
        this.tankControl = tankControl;
        tankControl.setTank(this);
    }

    public ResetFunction getResetFunction() {
        return resetFunction;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TankState getTankState() {
        return tankState;
    }

    public void setTankState(TankState tankState) {
        this.tankState = tankState;
    }

    public OnlyReadableTrack getTrack() {
        return track;
    }

    public int getId() {
        return id;
    }
}
