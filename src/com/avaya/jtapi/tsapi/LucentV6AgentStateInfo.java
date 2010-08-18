package com.avaya.jtapi.tsapi;

public class LucentV6AgentStateInfo extends LucentV5AgentStateInfo {
	public int pendingState;
	public int pendingReasonCode;

	public LucentV6AgentStateInfo(int _state, int _workMode, int _reasonCode,
			int _pendingState, int _pendingReasonCode) {
		super(_state, _workMode, _reasonCode);
		pendingState = _pendingState;
		pendingReasonCode = _pendingReasonCode;
	}
}

