package com.kirill.simulator.core.agents;

import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankControl;
import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.interfaces.OnlyReadableTrack;
import com.kirill.simulator.core.math.Vector2f;
import com.kirill.simulator.core.sensors.SharpManager;

import java.awt.*;

/**
 * Created by kirill-good on 25.2.15.
 */
public class SimpleTank implements Agent {
    public static enum TankState{
        FORWARD,BACKWARD,LEFTTURN,RIGHTTURN,STOP;
    };
    protected static int carId=0;
    protected final int id = carId++;
    protected Vector2f pos = new Vector2f(0,0)
                     , dir = new Vector2f(1,0);
    protected int length=50;
    protected double maxSpeed;
    protected SimpleTankControl simpleTankControl;
    protected SharpManager sharpManager = new SharpManager();
    protected OnlyReadableTrack track = null;
    protected double leftOfPath = 100;

    public SimpleTank(SimpleTankControl simpleTankControl) {
        this.simpleTankControl = simpleTankControl;
        this.simpleTankControl.setTank(this);
        this.maxSpeed = 1;
    }
    protected TankState tankState = TankState.STOP;

    @Override
    public void tick() {
        sharpManager.tick(track,pos,dir,length);
        simpleTankControl.tick();
        switch (tankState){
            case STOP:

                break;
            case FORWARD:
                this.pos.setXY( this.pos.getX() + this.maxSpeed * this.dir.getX()
                               ,this.pos.getY() + this.maxSpeed * this.dir.getY() );
                break;
            case BACKWARD:
                this.pos.setXY(this.pos.getX() - this.maxSpeed * this.dir.getX()
                        , this.pos.getY() - this.maxSpeed * this.dir.getY());
                break;
            case LEFTTURN:
                this.dir.turn(-Math.PI/180);
                break;
            case RIGHTTURN:
                this.dir.turn(+Math.PI/180);
                break;
            default:

        }
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
    public void paint(Graphics g, int DX, int DY) {
        int x1 = (int)(this.pos.getX() + DX - ( dir.getX() * length / 2) );
        int y1 = (int)(this.pos.getY() + DY - ( dir.getY() * length / 2) );
        int x2 = (int)(this.pos.getX() + DX + ( dir.getX() * length / 2) );
        int y2 = (int)(this.pos.getY() + DY + ( dir.getY() * length / 2) );
        g.drawString(String.valueOf(id),(x1+x2)/2,(y1+y2)/2);
        g.drawLine(x1,y1,x2,y2);
    }

    @Override
    public void setPos(double x, double y) {
        pos.setXY(x,y);
    }

    public TankState getTankState() {
        return tankState;
    }

    public void setTankState(TankState tankState) {
        this.tankState = tankState;
    }
}
