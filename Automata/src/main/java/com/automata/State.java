/**
 * 
 */
package com.automata;

import java.util.ArrayList;

/**
 * @author Miguel
 *
 */
public class State implements IState{

	private ArrayList<ITransition> transitions;

	/**
	 * 
	 */
	public State() {
		transitions = new ArrayList<ITransition>();
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ITransition> getTransitions() {
		// TODO Auto-generated method stub
		return transitions;
	}

	
	public void addTransition(IState to, ITransition transition) {
		// TODO Auto-generated method stub
		
	}	
}
