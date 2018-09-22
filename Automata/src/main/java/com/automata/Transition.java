/**
 * 
 */
package com.automata;


/**
 * @author Miguel
 *
 */
public class Transition implements ITransition {
	private String stimulus;
	private IState toState;
	
	public Transition(String s,IState fin) {
		stimulus=s;
		toState=fin;
	}
	public String getstimulus() {
		return stimulus;
	}
	public void setstimulus(String st) {
		stimulus=st;
	}
	public IState getStateFinal() {
		return toState;
	}
	public void setStateFinal(IState fin) {
		toState=fin;
	}
	
}
