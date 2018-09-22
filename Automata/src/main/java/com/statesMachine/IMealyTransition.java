package com.statesMachine;

import com.automata.ITransition;

public interface IMealyTransition extends ITransition {

	public String getResponse();
	public void setResponse(String rs);
}
