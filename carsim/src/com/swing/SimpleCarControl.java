package com.swing;

import com.sim.core.items.Car;
import com.sim.core.interfaces.CarControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kirill-good on 4.2.15.
 */
public class SimpleCarControl implements CarControl {
    private JSlider sl1 = new JSlider(0,-100,100,0);
    private JSlider sl2 = new JSlider(1,-100,100,0);
    private JButton b1 = new JButton();
    private JButton b2 = new JButton();
    private JFrame frame = new JFrame();
    private BListener listener1 = new BListener(b1,sl1);
    private BListener listener2 = new BListener(b2,sl2);

    public SimpleCarControl() {
        frame.setSize(300,300);
        frame.setLayout(new GridLayout(2, 2));
        b1.addActionListener(listener1);
        b2.addActionListener(listener2);
        frame.add(sl1);
        frame.add(sl2);
        frame.add(b1);
        frame.add(b2);
        frame.setVisible(true);
    }
    @Override
    public void tick(Car car) {
        frame.setTitle(-sl1.getValue()+" "+sl2.getValue());
        car.setWheelsAngle(-sl1.getValue());
        car.setSpeed(sl2.getValue());
    }
}

class BListener implements ActionListener{
    private JButton button;
    private JSlider slider;

    BListener(JButton button, JSlider slider) {
        this.button = button;
        this.slider = slider;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getSource()==button){
            slider.setValue(
                    ( slider.getMaximum() + slider.getMinimum() ) / 2
            );
        }
    }
}