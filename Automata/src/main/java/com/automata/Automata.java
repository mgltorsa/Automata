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
	private Alphabet language;
	private IState initState;
	private HashMap<String,IState> states;
	

	public Automata() {
		this("DefAutomaton");
	}
	
	
	public Automata(String id) {
		this.id=id;
		language = new Alphabet();
		states = new HashMap<String, IState>();
	}
	
	
	public void setId(String id) {
		this.id=id;
	}
	
	public  String getId() {
		return id;
	}
	public void addState(String id) {
		
	}	

	
	public IState getState(String name) {
		return states.get(name);
	}
	
	public IState getInitState() {
		return initState;
	}
	
	public HashMap<String ,ITransition> getTransitions(IState state) {
		return state.getTransitions();
	}


	public String getLanguage() {
		return language.toString();
	}


	public void setLanguage(String language) {
		this.language= new Alphabet(language);
		
	}

	/**retornar camino
	 * 
	 * @param stimulus
	 * @return
	 */
	public ArrayList<IState> getStates(String stimulus) {
		// TODO Auto-generated method stub
		return null;
	}


	public IAutomata getEquivalent() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void addTransition(IState from, ITransition transition) {
		// TODO Auto-generated method stub

		from.addTransition(transition);
	}

	public IState transitionFunction(IState state, String stimulus) {
		return state.getTransitions().get(stimulus).getStateFinal();
	}

	public ArrayList<IState> getExtendendTransition(String stimulus) {
		return getExtendendTransition(initState, stimulus);
	}

	public ArrayList<IState> getExtendendTransition(IState state, String stimulus) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String,IState> getStates() {
		return states;
	}

	public void setInitialState(IState state) {
		initState=state;
		String id=state.getId();
		if(!states.containsKey(id)) {
			states.put(id, state);
		}
	}

	public void setInitialState(String id) {
		initState=states.get(id);

	}

	public void addAll(IState... states) {
		for(IState state : states) {
			this.states.put(state.getId(), state);
		}
		
	}	
}
