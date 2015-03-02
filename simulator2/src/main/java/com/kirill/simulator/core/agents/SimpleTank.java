package com.kirill.simulator.core.agents;

import com.kirill.simulator.core.agents.controls.simpletank.SimpleTankControl;
import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.interfaces.OnlyReadableTrack;
import com.kirill.simulator.core.interfaces.ResetFunction;
import com.kirill.simulator.core.math.Vector2f;
import com.kirill.simulator.core.sensors.Sharp;
import com.kirill.simulator.core.sensors.SharpManager;

import javax.media.opengl.GL2;
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
    protected double width = 5;
    protected double maxSpeed;
    protected SimpleTankControl simpleTankControl;
    protected SharpManager sharpManager = new SharpManager();
    protected OnlyReadableTrack track = null;
    protected double leftOfPath = 100;

    public SimpleTank(SimpleTankControl simpleTankControl) {
        this.simpleTankControl = simpleTankControl;
        this.simpleTankControl.setTank(this);
        this.maxSpeed = 3;
    }
    protected TankState tankState = TankState.STOP;
    protected ResetFunction resetFunction = new ResetFunction() {
        @Override
        public void reset(Agent agent) {

        }
    };

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
        double halfLength = length /2;
        double tmpX = ( -dir.getY()*width );
        double tmpY = ( +dir.getX()*width );
        int x1 = (int)(pos.getX() + dir.getX() * halfLength + tmpX)
                ,y1 = (int)(pos.getY() + dir.getY() * halfLength + tmpY);
        int x2 = (int)(pos.getX() + dir.getX() * halfLength - tmpX)
                ,y2 = (int)(pos.getY() + dir.getY() * halfLength - tmpY);
        int x3 = (int)(x1 - length * dir.getX())
                ,y3 = (int)(y1 - length * dir.getY());
        int x4 = (int)(x2 - length * dir.getX())
                ,y4 = (int)(y2 - length * dir.getY());
        return track.getPix(x1,y1)
             ||track.getPix(x2,y2)
             ||track.getPix(x3,y3)
             ||track.getPix(x4,y4);
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
    public void glPaint(GL2 gl, int dX, int dY, int dZ) {
        double halfLength = length /2;

        double tmpX = ( -dir.getY()*width );
        double tmpY = ( +dir.getX()*width );

        int x1 = (int)(pos.getX() + dir.getX() * halfLength + tmpX)
           ,y1 = (int)(pos.getY() + dir.getY() * halfLength + tmpY);

        int x2 = (int)(pos.getX() + dir.getX() * halfLength - tmpX)
           ,y2 = (int)(pos.getY() + dir.getY() * halfLength - tmpY);

        int x3 = (int)(x1 - length * dir.getX())
           ,y3 = (int)(y1 - length * dir.getY());
        int x4 = (int)(x2 - length * dir.getX())
           ,y4 = (int)(y2 - length * dir.getY());

        gl.glColor3f(0.0f, 1.0f, 1.0f);
        gl.glVertex3f(x1 + dX,  y1 + dY,      +dZ);
        gl.glVertex3f(x2 + dX,  y2 + dY,      +dZ);
        gl.glVertex3f(x4 + dX,  y4 + dY,    +dZ);
        gl.glVertex3f(x3 + dX,  y3 + dY,    +dZ);
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

    public TankState getTankState() {
        return tankState;
    }

    public void setTankState(TankState tankState) {
        this.tankState = tankState;
    }

    public void setResetFunction(ResetFunction resetFunction) {
        this.resetFunction = resetFunction;
    }
}
