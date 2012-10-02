package com.avaya.jtapi.tsapi;

public class LucentAgentStateInfo {
	public int state;
	public int workMode;

	public LucentAgentStateInfo(int _state, int _workMode) {
		this.state = _state;
		this.workMode = _workMode;
	}
}