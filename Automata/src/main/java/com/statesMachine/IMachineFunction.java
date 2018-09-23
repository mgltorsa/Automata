/**
 * 
 */
package com.statesMachine;

import com.automata.IAutomata;
import com.automata.IState;

/**
 * @author Miguel
 *
 */
public interface IMachineFunction extends IAutomata{

	/**
	 * method used for get the response with stateInitial and a stimulus
	 * @param state state initial state !=null
	 * @param stimulus stimulus used for get response
	 * @return response 
	 */
	public String function(IState state, String stimulus);
}
