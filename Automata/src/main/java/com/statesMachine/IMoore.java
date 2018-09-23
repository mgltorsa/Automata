package com.statesMachine;


public interface IMoore extends IMachineFunction{
	/**
	 * adding a state of type MooreState.
	 * @param id. id of the State. id != null
	 * @param response. response of the state response !=null
	 */
	public void addState(String id,String response);

}
