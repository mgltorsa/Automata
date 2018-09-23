package com.statesMachine;

public interface IResponse {
	/**
	 * get response of the transition
	 * @return response
	 */
	public String getResponse();
	/**
	 * change the response of the transition
	 * @param rs new response
	 */
	public void setResponse(String rs);
}
