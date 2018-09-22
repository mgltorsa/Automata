/**
 * 
 */
package com.automata;

import java.util.HashMap;

/**
 * @author Miguel
 *
 */
public interface IAutomata {

	public void setId(String id);
	public String getId();
	public void addState(String id);
	public void addAll(IState...states);
	public void setInitialState(IState state);
	public void setInitialState(String id);
	public void addTransition(IState from, ITransition transition);
	public IState transitionFunction(IState state, String stimulus);
	public IState getState(String id);
	public IState getInitState();
	public String getLanguage();
	public void setLanguage(String language);
	public HashMap<String,IState> getStates();
	public IAutomata getEquivalent();

	
}
