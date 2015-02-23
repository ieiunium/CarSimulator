package com.kirill.simulator.runner;

import com.kirill.simulator.core.simulation.Track;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Track track = new Track(10,10);
        String s = "track3";
        track.loadFromFile(s+".map");
        track.saveToBMP(s+".png");
        System.out.println( "Hello World!" );
    }
}
