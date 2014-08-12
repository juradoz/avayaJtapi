package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV6Agent;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV6AgentImpl extends LucentAgentImpl implements LucentV6Agent {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV6AgentImpl)) {
			return this.tsAgent.equals(((LucentV6AgentImpl) obj).tsAgent);
		}

		return false;
	}

	LucentV6AgentImpl(TSAgent _tsAgent) {
		super(_tsAgent);
		TsapiTrace.traceConstruction(this, LucentV6AgentImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV6AgentImpl.class);
	}
}