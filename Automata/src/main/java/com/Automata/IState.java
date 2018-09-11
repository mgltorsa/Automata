/**
 * 
 */
package com.Automata;

import java.util.ArrayList;

/**
 * @author Miguel
 *
 */
public interface IState {

	public void setId(String id);
	public String getId();
	public ArrayList<ITransition> getTransitions();
	public void addTransition(IState to, ITransition transition);
}
