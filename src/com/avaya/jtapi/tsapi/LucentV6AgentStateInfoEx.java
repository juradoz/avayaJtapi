package com.avaya.jtapi.tsapi;

public class LucentV6AgentStateInfoEx extends LucentV6AgentStateInfo {
	public int lucentWorkMode;
	public static final short WM_NONE = -1;
	public static final short WM_AUX_WORK = 1;
	public static final short WM_AFTCAL_WK = 2;
	public static final short WM_AUTO_IN = 3;
	public static final short WM_MANUAL_IN = 4;

	public LucentV6AgentStateInfoEx(int _state, int _workMode, int _reasonCode,
			int _pendingState, int _pendingReasonCode, int _lucentWorkMode) {
		super(_state, _workMode, _reasonCode, _pendingState, _pendingReasonCode);
		lucentWorkMode = _lucentWorkMode;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV6AgentStateInfoEx JD-Core Version: 0.5.4
 */