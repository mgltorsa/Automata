package com.statesMachine;

import com.automata.IState;

public interface IMealy extends IMachineFunction {

	/**
	 * adding transition of type Mealy transition
	 * @param stateInitial state where state the transition. stateInitial != null
	 * @param stateFinal state where arrive the transition. stateFinal != null
	 * @param stimulus stimulus which the stateInitial can arrive in stateFinal.stimulus != nul
	 * @param response response of the transition.
	 */
	public void addTransition(IState stateInitial,IState stateFinal,String stimulus,String response);
}
