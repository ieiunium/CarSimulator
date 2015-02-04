package com.sim.core.items;

import java.io.*;

/**
 * Created by kirill-good on 4.2.15.
 */
public class Track {
    private boolean field[][];
    private int n,m;
    public Track(int n,int m){
        this.n=n;
        this.m=m;
        clear();
    }
    public void clear(){
        field = new boolean[n][];
        for(int i = 0; i<n; i++){
            field[i] = new boolean[m];
            for(int j = 0; j<n; j++){
                field[i][j]=false;
            }
        }
    }
    public void saveToFile(String fileName){
        File f = new File(fileName);
        OutputStream os = null;
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.println(n);
            pw.println(m);
            for(int i = 0; i<n; i++){
                for(int j = 0; j<n; j++){
                    pw.print(field[i][j]?1:0);
                }
                pw.println();
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void loadFromFile(String fileName){
        File f = new File(fileName);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            n = Integer.parseInt(br.readLine());
            m = Integer.parseInt(br.readLine());
            clear();
            for(int i=0;i<n;i++){
                String buf = br.readLine();
                for(int j = 0;j<m;j++){
                    field[i][j] = buf.charAt(j)=='1';
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }
}
