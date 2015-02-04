package com.sim.core.items.math;

/**
 * Created by kirill-good on 3.2.15.
 */
public class Vector2f{
    private double x,y;

    public void turn(double angle){
        double tx = x * Math.cos(angle) - y * Math.sin(angle);
        double ty = x * Math.sin(angle) + y * Math.cos(angle);
        x=tx;
        y=ty;
    }

    public void  normalization(){
        double l = Math.hypot(x,y);
        double tx = x/l;
        double ty = y/l;
        x=tx;
        y=ty;
    }

    public Vector2f(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
