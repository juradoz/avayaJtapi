package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentAgentImpl extends TsapiAgent implements LucentAgent {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentAgentImpl)) {
			return this.tsAgent.equals(((LucentAgentImpl) obj).tsAgent);
		}

		return false;
	}

	LucentAgentImpl(TSAgent _tsAgent) {
		super(_tsAgent);
		TsapiTrace.traceConstruction(this, LucentAgentImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentAgentImpl.class);
	}
}