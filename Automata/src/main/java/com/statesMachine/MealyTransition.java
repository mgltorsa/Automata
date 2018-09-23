/**
 * 
 */
package com.statesMachine;

import com.automata.IState;
import com.automata.Transition;

/**
 * @author Miguel
 *
 */
public class MealyTransition extends Transition implements IMealyTransition {
	private String response;
	
	public MealyTransition(String stimulus,String response, IState fin) {
		super(stimulus, fin);
		this.response=response;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String rs) {
		response=rs;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"|"+response;
	}
}
