package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentStateConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentStateConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class QueryAgentStateConfHandler2 implements ConfHandler {
	TSDevice device;
	String acdName;
	String agentID;
	int agentState = -1;
	int workMode = 0;
	int state = 0;

	QueryAgentStateConfHandler2(TSDevice _device, String _acdName,
			String _agentID) {
		device = _device;
		acdName = _acdName;
		agentID = _agentID;
	}

	int getAgentState() {
		return agentState;
	}

	int getState() {
		return state;
	}

	int getWorkMode() {
		return workMode;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAQueryAgentStateConfEvent))) {
			return;
		}

		CSTAQueryAgentStateConfEvent agentStateConf = (CSTAQueryAgentStateConfEvent) event
				.getEvent();
		agentState = agentStateConf.getAgentState();
		if ((agentState == 1)
				|| (!(event.getPrivData() instanceof LucentQueryAgentStateConfEvent))) {
			return;
		}
		short tsapiWorkMode = ((LucentQueryAgentStateConfEvent) event
				.getPrivData()).getWorkMode();
		if (tsapiWorkMode == 3) {
			workMode = 1;
		} else if (tsapiWorkMode == 4) {
			workMode = 2;
		}
		short talkState = ((LucentQueryAgentStateConfEvent) event.getPrivData())
				.getTalkState();
		if (talkState == 0) {
			state = 7;
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.QueryAgentStateConfHandler2 JD-Core Version:
 * 0.5.4
 */