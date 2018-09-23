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
	
	public Transition(String stimulus,IState fin) {
		this.stimulus=stimulus;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stimulus;
	}
}
