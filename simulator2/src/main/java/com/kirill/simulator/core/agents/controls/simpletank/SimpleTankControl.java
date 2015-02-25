package com.kirill.simulator.core.agents.controls.simpletank;

import com.kirill.simulator.core.agents.SimpleTank;
import com.kirill.simulator.core.interfaces.AgentControl;

/**
 * Created by kirill-good on 25.2.15.
 */
public interface SimpleTankControl extends AgentControl {
    public void setTank(SimpleTank simpleTank);
}
