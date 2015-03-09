package com.sim.core.agents.tank;

import com.sim.core.interfaces.AgentControl;
import com.sim.core.interfaces.Chromosomal;

/**
 * Created by kirill-good on 9.3.15.
 */
public interface TankControl extends AgentControl,Chromosomal {
    public void setTank(Tank tank);
}
