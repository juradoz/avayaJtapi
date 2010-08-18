package com.avaya.jtapi.tsapi;

public class LucentV5AgentStateInfo extends LucentAgentStateInfoEx {
	public int reasonCode;

	public LucentV5AgentStateInfo(final int _state, final int _workMode,
			final int _reasonCode) {
		super(_state, _workMode);
		reasonCode = _reasonCode;
	}
}
