/**
 * 
 */
package com.StatesMachine;

import com.Automata.IState;

/**
 * @author Miguel
 *
 */
public interface IMachineFunction {

	public String function(IState state, String stimulus);
}
