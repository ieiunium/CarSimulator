package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.ThActivationFunction;

/**
 * Created by kirill-good on 30.3.15.
 */
public class MainXORBPL {
    static double dataIn[][] = {
            {0,0}
            ,{0,1}
            ,{1,0}
            ,{1,1}
    };
    static double dataOut[][] = {
            {0}
            ,{1}
            ,{1}
            ,{0}
    };
    public static void main(String[] args) {
        // write your code here
        long t = System.currentTimeMillis();

        int config[] = {2,2,1};
        NeuralNetwork nn = new NeuralNetwork(config, new ThActivationFunction(){
            public double F(double x) {
                return 1/(1+Math.exp(-x));
            }
        });
        System.out.println(nn.numOfGens());
        nn.bpl(dataIn,dataOut,0.01,0.11);
        for (int i = 0; i < dataIn.length; i++) {
            double out[] = nn.getOut(dataIn[i]);
            System.out.println(dataIn[i][0]+" "+dataIn[i][1]+" "+out[0]);
        }

        t = System.currentTimeMillis() - t;
        System.out.println( (t/1000)%60 + "s" );
        System.out.println( t/60000+ "m" );
        System.out.println( t/(1000 * 60 * 60)+ "h" );
    }
}
