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

	public String function(IState state, String stimulus);
}
