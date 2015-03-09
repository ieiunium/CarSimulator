package com.sim.core.agents.car;

import com.sim.core.interfaces.AgentControl;
import com.sim.core.interfaces.Chromosomal;

/**
 * Created by kirill-good on 4.2.15.
 */
public interface CarControl extends AgentControl,Chromosomal {
    public void setCar(Car car);
}
