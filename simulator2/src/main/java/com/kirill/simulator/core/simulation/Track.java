package com.kirill.simulator.core.simulation;

import com.kirill.simulator.core.interfaces.OnlyReadableTrack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by kirill-good on 23.2.15.
 */
public class Track implements OnlyReadableTrack {
    private boolean field[][];
    private int height, width;
    public Track(int width, int height){
        if(width<1){
            width=640;
        }
        if(height<1){
            height=480;
        }
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
                    pw.print(field[i][j] ? 1 : 0);
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
    public void saveToPNG(String fileName){

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        try {
            image.getGraphics().clearRect(0,0,width,height);
            for(int i = 0; i< height; i++){
                for(int j = 0; j< width; j++){
                    if(field[i][j]) {
                        image.setRGB(j,i, Color.BLACK.getRGB());
                    }else{
                        image.setRGB(j,i, Color.WHITE.getRGB());
                    }
                }
            }
            ImageIO.write(image,"png",new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadFromPNG(String fileName){



        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            height = image.getHeight();
            width = image.getWidth();
            clear();
            for(int i=0;i<height;i++){
                for(int j = 0;j< width;j++){
                    field[i][j] = image.getRGB(j,i)==Color.BLACK.getRGB();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean getPix(int i,int j){
        if(i>=0 && j>=0 && i<width && j<height) {
            return field[j][i];
        }else{
            return false;
        }
    }

    public void setPix(int i,int j, boolean p){
        if(i>=0 && j>=0 && i<width && j<height) {
            field[j][i] = p;
        }
    }
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }

}
