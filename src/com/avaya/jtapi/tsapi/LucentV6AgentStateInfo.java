package com.avaya.jtapi.tsapi;

public class LucentV6AgentStateInfo extends LucentV5AgentStateInfo {
	public int pendingState;
	public int pendingReasonCode;

	public LucentV6AgentStateInfo(final int _state, final int _workMode,
			final int _reasonCode, final int _pendingState,
			final int _pendingReasonCode) {
		super(_state, _workMode, _reasonCode);
		pendingState = _pendingState;
		pendingReasonCode = _pendingReasonCode;
	}
}
