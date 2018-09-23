/**
 * 
 */
package com.automata;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Miguel
 *
 */
public interface IAutomata extends Serializable {

	/**change id of the Automata
	 * 
	 * @param id new id of the Automata id != null
	 */
	public void setId(String id);
	/**get id of the automata
	 * 
	 * @return id of the automata
	 */
	public String getId();
	/**add a State into Automata
	 * 
	 * @param id is the id of the new State
	 */
	public void addState(String id);
	/**add a vector of States
	 * 
	 * @param states states which going to adding
	 */
	public void addAll(IState...states);
	/**
	 * change the state initial
	 * @param state new state initial.state != null
	 */
	public void setInitialState(IState state);
	/**
	 * change the state initial
	 * @param id id of the new state initial. id!=null
	 */
	public void setInitialState(String id);
	/**
	 * add transition between two State
	 * @param from State of start from != null
	 * @param transition transition for adding. transition!=null
	 */
	public void addTransition(IState from, ITransition transition); 
	/**
	 *get State which a state can arrive with a stimulus
	 * @param state state since where start the transition. state != null
	 * @param stimulus the which state need for arrive in other state. stimulus != null
	 * @return state final of the transition with state initial state and stimulus
	 */
	public IState transitionFunction(IState state, String stimulus);
	/**
	 * return the state with id same the parameter
	 * @param id. id of state. id!=null
	 * @return state with id equals the parameter. 
	 */
	public IState getState(String id);
	/**get initial state
	 * 
	 * @return initial state
	 */
	public IState getInitState();
	/**
	 * get Alphabet of the automata
	 * @return alphabet 
	 */
	public Alphabet getLanguage();
	/**
	 * change alphabet of automata
	 * @param language new alphabet. language != null.
	 */
	public void setLanguage(String language);
	/**
	 * return all state of the automata
	 * @return states
	 */
	public HashMap<String,IState> getStates();
	/**
	 * get automata minimum equivalent of the automata in instance
	 * @return automata minimum equivalent.
	 */
	public IAutomata getEquivalent();
	/**
	 * get Transition that start in a state with a stimulus.
	 * @param state stare state. state != null.
	 * @param stimulus stimulus with which arrive in other state
	 * @return Transition.
	 */
	public ITransition getTransition(IState state,String stimulus);
	/**
	 * remove state with id.
	 * @param id. id != null and id is not empty.
	 * @return Transition.
	 */
	public void removeState(String id);
	/**
	 * remove state with id.
	 * @param state. state != null.
	 * @return Transition.
	 */
	public void addState(IState state);
}
