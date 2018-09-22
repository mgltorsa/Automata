/**
 * 
 */
package com.automata;

import java.util.HashMap;

/**
 * @author Miguel
 *
 */
public interface IState {

	public void setId(String id);
	public String getId();
	public HashMap<String ,ITransition> getTransitions();
	public void addTransition(ITransition transition);
}
