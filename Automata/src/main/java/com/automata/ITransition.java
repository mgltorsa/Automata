/**
 * 
 */
package com.automata;

/**
 * @author Miguel
 *
 */
public interface ITransition {

	/**
	 * stimulus of transition
	 * @return stimulus
	 */
	public String getstimulus();
	/**
	 * change stimulus
	 * @param st new stimulus st != null.
	 */
	public void setstimulus(String st);
	/**
	 * get state final of transition.
	 * @return state final
	 */
	public IState getStateFinal();
	/**
	 * change state final of transition
	 * @param fin new state final. fin != null
	 */
	public void setStateFinal(IState fin);
	}
