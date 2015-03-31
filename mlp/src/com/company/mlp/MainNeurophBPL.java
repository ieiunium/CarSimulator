package com.company.mlp;

import com.company.mlp.math.genetics.Chromosome;
import com.company.mlp.nn.Image;
import com.company.mlp.nn.Plotter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill-good on 31.3.15.
 */
public class MainNeurophBPL {
    public static void main(String[] args) {

        // create training set (logical XOR function)
        DataSet trainingSet = new DataSet(81, 81);
        double inn[][] = new double[10][];
        for (int i = 0; i < 10; i++) {
            Image im = new Image(new File(i + ".png"));
            inn[i] = im.getImageF().clone();
            System.out.println(Arrays.toString(inn[i]));
        }
        for (int i = 0; i < 10; i++) {
            trainingSet.addRow(new DataSetRow(inn[i], inn[i]));
        }
        // create multi layer perceptron
        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 81, 20,10,20, 81);
        // learn the training set
        BackPropagation backPropagation = new BackPropagation();
        backPropagation.setMaxError(0.03);
        myMlPerceptron.setLearningRule(backPropagation);

        myMlPerceptron.learn(trainingSet);

        // test perceptron
        System.out.println("Testing trained neural network");
        testNeuralNetwork(myMlPerceptron, trainingSet);

        // save trained neural network
        //myMlPerceptron.save("myMlPerceptron.nnet");

        // load saved neural network
        //NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

        // test loaded neural network
        System.out.println("Testing loaded neural network");
        testNeuralNetwork(myMlPerceptron, trainingSet);

        /*Plotter plotter1 = new Plotter();
        List<Image> images = new ArrayList<Image>();
        for (int i = 0; i < 10; i++) {
            Image im = new Image(new File(i + ".png"));
            images.add(im);
            double in[] = im.getImageF().clone();
            double out[] = nn.getOut(in);
            for (int j = 0; j < in.length; j++) {
                System.out.println(in[j]+" "+out[j]);
            }
            System.out.println();
            //plotter1.getBufferedImageList().add(im.getImage());
            plotter1.getBufferedImageList().add(new Image(in, 9, 9).getImage());
            plotter1.getBufferedImageList().add(new Image(out, 9, 9).getImage());
        }
        plotter1.setVisible(true);*/
    }

    public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {

        for(DataSetRow dataRow : testSet.getRows()) {

            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            double[ ] networkOutput = nnet.getOutput();
            System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
            System.out.println(" Output: " + Arrays.toString(networkOutput) );

        }

    }
}
