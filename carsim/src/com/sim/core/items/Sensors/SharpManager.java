package com.sim.core.items.Sensors;

import com.sim.core.interfaces.OnlyReadableTrack;
import com.sim.core.items.Sensors.Sharp;
import com.sim.core.items.math.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 10.2.15.
 */
public class SharpManager {
    private List<Sharp> sharps = new ArrayList<Sharp>();
    private Sharp[] arrayOfSharps = {};
    public void addSharp(Sharp sharp){
        sharps.add(sharp);
        makeArray();
    }
    public void removeSharp(Sharp sharp){
        sharps.remove(sharp);
        makeArray();
    }
    public void removeSharp(int i){
        sharps.remove(i);
        makeArray();
    }
    public Sharp getSharp(int i){
        return sharps.get(i);
    }
    public Sharp[] getSharps(){
        return arrayOfSharps;
    }
    public void tick(OnlyReadableTrack track,Vector2f pos,Vector2f dir,double length){
        double x0,y0;
        x0 = pos.getX() + (length / 2) * dir.getX();
        y0 = pos.getY() + (length / 2) * dir.getY();
        Vector2f vector = new Vector2f(0,0);
        for(Sharp s: sharps){
            vector.setXY(dir);
            vector.turn(s.angle);
            vector.normalization();
            s.value = s.max;
            for(double i = s.min; i <= s.max; i+=0.1){
                int x = (int)( x0 + i * vector.getX() );
                int y = (int)( y0 + i * vector.getY() );
                if( track.getPix(x,y) ){
                    s.value = i;
                    break;
                }
            }
            System.out.println(s.value+" "+s.angle);
        }
        System.out.println();
    }
    protected void makeArray(){
        if(arrayOfSharps.length != sharps.size()){
            arrayOfSharps = new Sharp[sharps.size()];
        }
        for(int i = 0;i<sharps.size();i++){
            arrayOfSharps[i] = sharps.get(i);
        }
    }
}
