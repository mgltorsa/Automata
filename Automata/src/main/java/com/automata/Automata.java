/**
 * 
 */
package com.automata;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Miguel
 *
 */
public class Automata implements IAutomata{
	private String id;
	private Language language;
	private IState initState;
	private HashMap<String,IState> states;
	

	public Automata() {
		this("DefAutomaton");
	}
	
	
	public Automata(String id) {
		this.id=id;
		language = new Language();
		states = new HashMap<String, IState>();
	}
	
	
	public void setId(String id) {
		this.id=id;
	}
	
	public  String getId() {
		return id;
	}
	public void addState(String id) {
		// TODO Auto-generated method stub
		
	}	

	
	public IState getState(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IState getInitState() {
		//TODO
		return null;
	}
	
	public ArrayList<ITransition> getTransitions(IState state) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setLanguage(String language) {
		this.language= new Language(language);
		
	}


	public ArrayList<IState> getStates(String stimulus) {
		// TODO Auto-generated method stub
		return null;
	}


	public IAutomata getEquivalent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTransition(IState from, IState to, ITransition transition) {
		// TODO Auto-generated method stub
		
	}

	public IState transitionFunction(IState state, String stimulus) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<IState> getExtendendTransition(String stimulus) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<IState> getExtendendTransition(IState state, String stimulus) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<IState> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInitialState(IState state) {
		// TODO Auto-generated method stub
		
	}

	public void setInitialState(String id) {
		// TODO Auto-generated method stub
		
	}

	public void addAll(IState... states) {
		for(IState state : states) {
			this.states.put(state.getId(), state);
		}
		
	}
	
	
}