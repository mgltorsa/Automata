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
public class MealyTransition extends Transition implements IMealyTransition{
	private String response;
	
	public MealyTransition(String s,String response, IState fin) {
		super(s, fin);
		this.response=response;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String rs) {
		response=rs;
	}

}
