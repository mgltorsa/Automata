/**
 * 
 */
package com.Automata;

import java.util.ArrayList;

/**
 * @author Miguel
 *
 */
public interface IAutomata {

	public void addState(String id);
	public void setInitialState(IState state);
	public void setInitialState(String id);
	public void addTransition(IState from, IState to, ITransition transition);
	public IState transitionFunction(IState state, String stimulus);
	public IState getState(String id);
	public ArrayList<ITransition> getTransitions(IState state);
	public String getLanguage();
	public void setLanguage(String language);
	public ArrayList<IState> getStates();
	public ArrayList<IState> getExtendendTransition(String stimulus);
	public ArrayList<IState> getExtendendTransition(IState state, String stimulus);
	public IAutomata getEquivalent();

	
}
