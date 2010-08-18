package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV6Agent;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV6AgentImpl extends LucentAgentImpl implements LucentV6Agent {
	LucentV6AgentImpl(TSAgent _tsAgent) {
		super(_tsAgent);
		TsapiTrace.traceConstruction(this, LucentV6AgentImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV6AgentImpl) {
			return tsAgent.equals(((LucentV6AgentImpl) obj).tsAgent);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV6AgentImpl.class);
	}
}

