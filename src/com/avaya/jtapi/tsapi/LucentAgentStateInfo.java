package com.avaya.jtapi.tsapi;

public class LucentAgentStateInfo {
	public int state;
	public int workMode;

	public LucentAgentStateInfo(final int _state, final int _workMode) {
		state = _state;
		workMode = _workMode;
	}
}
