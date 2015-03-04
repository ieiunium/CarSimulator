package com.kirill.simulator.core.agents.controls.car;

import com.kirill.simulator.core.agents.Car;
import com.kirill.simulator.core.interfaces.AgentControl;

/**
 * Created by kirill-good on 4.3.15.
 */
public interface CarControl extends AgentControl {
    public void setCar(Car car);
}
