/**
 * 
 */
package com.StatesMachine;

import com.Automata.IState;

/**
 * @author Miguel
 *
 */
public class MachineFunction implements IMachineFunction{
	
	private IStatesMachine statesMachine;


	public MachineFunction(IStatesMachine machine) {
		this.statesMachine=machine;
	}
	
	public String function(IState state, String stimulus) {
		// TODO Auto-generated method stub
		return statesMachine.toString();
	}

}
