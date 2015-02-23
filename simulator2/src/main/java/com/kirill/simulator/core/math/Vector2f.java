package com.kirill.simulator.core.math;

/**
 * Created by kirill-good on 23.2.15.
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
    public void setXY(double x,double y){

        this.x = x;
        this.y = y;
    }
    public void setXY(Vector2f vector){

        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2f getCopy(){

        return new Vector2f(x, y);
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
