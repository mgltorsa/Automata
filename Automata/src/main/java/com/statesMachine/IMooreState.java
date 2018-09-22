package com.statesMachine;

import com.automata.*;

public interface IMooreState extends IState {

	public String getResponse();
	public void setResponse(String rs);
}
