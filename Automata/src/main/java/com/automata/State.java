/**
 * 
 */
package com.automata;

import java.util.HashMap;

/**
 * @author Miguel
 *
 */
public class State implements IState{
	private String id;
	private HashMap<String ,ITransition> transitions;

	/**
	 * 
	 */
	
	public State(String id) {
		transitions = new HashMap<String,ITransition>();
		this.id=id;
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public HashMap<String ,ITransition> getTransitions() {
		// TODO Auto-generated method stub
		return transitions;
	}

	
	public void addTransition( ITransition transition) {
		// TODO Auto-generated method stub
		transitions.put(transition.getstimulus(),transition);
		
	}	
}
