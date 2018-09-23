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
public interface IState extends Serializable {

	/**change the id of the state
	 * 
	 * @param id new id. id != null
	 */
	public void setId(String id);
	/**
	 * get id of state
	 * @return id
	 */
	public String getId();
	/**
	 * get all transition that the start is the state
	 * @return all transitions
	 */
	public HashMap<String ,ITransition> getTransitions();
	/**
	 * add a new transition 
	 * @param transition. new transition transition !=null
	 */
	public void addTransition(ITransition transition);
}
