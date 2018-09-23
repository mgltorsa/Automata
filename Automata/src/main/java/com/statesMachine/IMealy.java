package com.statesMachine;

import com.automata.IState;

public interface IMealy extends IMachineFunction {

	public void addTransition(IState stateInitial,IState stateFinal,String stimulus,String response);
}
