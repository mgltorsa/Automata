/**
 * 
 */
package com.automata;

/**
 * @author Miguel
 *
 */
public interface ITransition {

	public String getstimulus();
	public void setstimulus(String st);
	public IState getStateFinal();
	public void setStateFinal(IState fin);
	}
