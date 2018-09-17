/**
 * 
 */
package com.statesMachine;

import com.automata.IState;

/**
 * @author Miguel
 *
 */
public interface IMachineFunction {

	public String function(IState state, String stimulus);
}
