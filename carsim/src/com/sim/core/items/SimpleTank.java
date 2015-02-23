package com.sim.core.items;

/**
 * Created by kirill-good on 23.2.15.
 */
public class SimpleTank extends Tank {
    public static enum TankState{
        FORWARD,BACKWARD,LEFTTURN,RIGHTTURN,STOP;
    };

    public SimpleTank() {
        this.maxSpeed = 1;
    }

    protected TankState tankState = TankState.STOP;

    @Override
    public void tick() {
        sharpManager.tick(track,pos,dir,length);
        carControl.tick(this);
        switch (tankState){
            case STOP:

                break;
            case FORWARD:
                this.setPos( this.getPos().getX() + this.maxSpeed * this.dir.getX()
                            ,this.getPos().getY() + this.maxSpeed * this.dir.getY() );
                break;
            case BACKWARD:
                this.setPos( this.getPos().getX() - this.maxSpeed * this.dir.getX()
                            ,this.getPos().getY() - this.maxSpeed * this.dir.getY() );
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

    public TankState getTankState() {
        return tankState;
    }

    public void setTankState(TankState tankState) {
        this.tankState = tankState;
    }

    @Override
    public void setAction(int in1, int in2) {
        switch (in1){
            case 0:
                tankState = TankState.FORWARD;
                break;
            case 1:
                tankState = TankState.BACKWARD;
                break;
            case 2:
                tankState = TankState.LEFTTURN;
                break;
            case 3:
                tankState = TankState.RIGHTTURN;
                break;

        }
    }
}
