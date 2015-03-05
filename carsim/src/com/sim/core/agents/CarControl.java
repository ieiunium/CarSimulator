package com.sim.core.agents;

import com.sim.core.agents.Car;
import com.sim.core.interfaces.AgentControl;
import com.sim.core.interfaces.Chromosomal;
import sun.management.*;
import sun.management.Agent;

/**
 * Created by kirill-good on 4.2.15.
 */
public interface CarControl extends AgentControl,Chromosomal {
    public void setCar(Car car);
}
