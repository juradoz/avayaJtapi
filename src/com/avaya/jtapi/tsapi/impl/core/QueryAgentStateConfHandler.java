package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentStateConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentStateConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5QueryAgentStateConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV6QueryAgentStateConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class QueryAgentStateConfHandler implements ConfHandler {
	TSAgent agent;

	QueryAgentStateConfHandler(TSAgent _agent) {
		agent = _agent;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAQueryAgentStateConfEvent))) {
			return;
		}

		CSTAQueryAgentStateConfEvent agentStateConf = (CSTAQueryAgentStateConfEvent) event
				.getEvent();
		int agentState = agentStateConf.getAgentState();
		int _workMode = 0;
		int _lucentworkmode = -1;
		int _reasonCode = 0;
		int _pendingState = 0;
		int _pendingReasonCode = 0;
		boolean agentIsBusy = false;
		if (event.getPrivData() instanceof LucentQueryAgentStateConfEvent) {
			short tsapiWorkMode = ((LucentQueryAgentStateConfEvent) event
					.getPrivData()).getWorkMode();

			_lucentworkmode = tsapiWorkMode;

			if (tsapiWorkMode == 3) {
				_workMode = 1;
			} else if (tsapiWorkMode == 4) {
				_workMode = 2;
			}
			short talkState = ((LucentQueryAgentStateConfEvent) event
					.getPrivData()).getTalkState();
			if (talkState == 0) {
				agentIsBusy = true;
			}
			if (event.getPrivData() instanceof LucentV5QueryAgentStateConfEvent) {
				_reasonCode = ((LucentV5QueryAgentStateConfEvent) event
						.getPrivData()).getReasonCode();
				if (event.getPrivData() instanceof LucentV6QueryAgentStateConfEvent) {
					int pendingWorkMode = ((LucentV6QueryAgentStateConfEvent) event
							.getPrivData()).getPendingWorkMode();
					if (pendingWorkMode == 1) {
						_pendingState = 3;
					} else if (pendingWorkMode == 2) {
						_pendingState = 5;
					}
					_pendingReasonCode = ((LucentV6QueryAgentStateConfEvent) event
							.getPrivData()).getPendingReasonCode();
				}
			}
		}
		if (agentIsBusy) {
			agent.updateState(7, _workMode, _reasonCode, _pendingState,
					_pendingReasonCode, _lucentworkmode, null);
		} else {
			switch (agentState) {
			case 0:
				agent.updateState(3, _workMode, _reasonCode, _pendingState,
						_pendingReasonCode, _lucentworkmode, null);
				break;
			case 1:
				agent.updateState(2, _workMode, _reasonCode, _pendingState,
						_pendingReasonCode, _lucentworkmode, null);
				break;
			case 2:
				agent.updateState(4, _workMode, _reasonCode, _pendingState,
						_pendingReasonCode, _lucentworkmode, null);
				break;
			case 3:
				agent.updateState(5, _workMode, _reasonCode, _pendingState,
						_pendingReasonCode, _lucentworkmode, null);
				break;
			case 4:
				agent.updateState(6, _workMode, _reasonCode, _pendingState,
						_pendingReasonCode, _lucentworkmode, null);
				break;
			default:
				agent.updateState(0, _workMode, _reasonCode, _pendingState,
						_pendingReasonCode, _lucentworkmode, null);
			}
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.QueryAgentStateConfHandler JD-Core Version:
 * 0.5.4
 */