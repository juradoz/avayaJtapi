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

	QueryAgentStateConfHandler2(final TSDevice _device, final String _acdName,
			final String _agentID) {
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

	@Override
	public void handleConf(final CSTAEvent event) {
		if (event == null
				|| !(event.getEvent() instanceof CSTAQueryAgentStateConfEvent))
			return;

		final CSTAQueryAgentStateConfEvent agentStateConf = (CSTAQueryAgentStateConfEvent) event
				.getEvent();
		agentState = agentStateConf.getAgentState();
		if (agentState == 1
				|| !(event.getPrivData() instanceof LucentQueryAgentStateConfEvent))
			return;
		final short tsapiWorkMode = ((LucentQueryAgentStateConfEvent) event
				.getPrivData()).getWorkMode();
		if (tsapiWorkMode == 3)
			workMode = 1;
		else if (tsapiWorkMode == 4)
			workMode = 2;
		final short talkState = ((LucentQueryAgentStateConfEvent) event
				.getPrivData()).getTalkState();
		if (talkState == 0)
			state = 7;
	}
}
