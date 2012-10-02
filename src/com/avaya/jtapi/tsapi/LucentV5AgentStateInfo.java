package com.avaya.jtapi.tsapi;

public class LucentV5AgentStateInfo extends LucentAgentStateInfoEx {
	public int reasonCode;

	public LucentV5AgentStateInfo(int _state, int _workMode, int _reasonCode) {
		super(_state, _workMode);
		this.reasonCode = _reasonCode;
	}
}