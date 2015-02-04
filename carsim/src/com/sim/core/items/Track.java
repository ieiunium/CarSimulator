package com.sim.core.items;

import java.io.*;

/**
 * Created by kirill-good on 4.2.15.
 */
public class Track {
    private boolean field[][];
    private int height, width;
    public Track(int width, int height){
        this.height = height;
        this.width = width;
        clear();
    }
    public void clear(){
        field = new boolean[height][];
        for(int y = 0; y< height; y++){
            field[y] = new boolean[width];
            for(int x = 0; x< width; x++){
                field[y][x]=false;
            }
        }
    }
    public void saveToFile(String fileName){
        File f = new File(fileName);
        OutputStream os = null;
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.println(height);
            pw.println(width);
            for(int i = 0; i< height; i++){
                for(int j = 0; j< width; j++){
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
            height = Integer.parseInt(br.readLine());
            width = Integer.parseInt(br.readLine());
            clear();
            for(int i=0;i< height;i++){
                String buf = br.readLine();
                for(int j = 0;j< width;j++){
                    field[i][j] = buf.charAt(j)=='1';
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean getPix(int i,int j){
        return field[j][i];
    }
    public void setPix(int i,int j, boolean p){
        field[j][i]=p;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
