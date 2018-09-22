/**
 * 
 */
package com.statesMachine;

import com.automata.State;

/**
 * @author Miguel
 *
 */
public class MooreState extends State implements IMooreState{
	private String response;

	public MooreState(String id,String res) {
		super(id);
		response=res;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String rs) {
		response=rs;
	}


}
