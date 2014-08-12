package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV7Agent;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV7AgentImpl extends LucentV6AgentImpl implements
		LucentV7Agent {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV7AgentImpl)) {
			return this.tsAgent.equals(((LucentV7AgentImpl) obj).tsAgent);
		}

		return false;
	}

	LucentV7AgentImpl(TSAgent _tsAgent) {
		super(_tsAgent);
		TsapiTrace.traceConstruction(this, LucentV7AgentImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7AgentImpl.class);
	}
}