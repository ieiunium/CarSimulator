package com.company.mlp.math.neural;

/**
 * Created by kirill on 16.4.15.
 */
public class Example {
    protected double in[];
    protected double out[];

    public Example(double[] in, double[] out) {
        this.in = in;
        this.out = out;
    }

    public double[] getIn() {
        return in;
    }
    public void setIn(double[] in) {
        this.in = in;
    }
    public double[] getOut() {
        return out;
    }
    public void setOut(double[] out) {
        this.out = out;
    }

    public double getE(double out[]){
        double res = 0;
        if(this.out.length!=out.length){
            throw new RuntimeException("in.length!=out.length");
        }
        for (int i = 0; i < out.length; i++) {
            res += Math.pow(this.out[i]-out[i],2);
        }
        return res;
    }
}
