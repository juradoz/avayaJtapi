package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentAgentImpl extends TsapiAgent implements LucentAgent {
	LucentAgentImpl(TSAgent _tsAgent) {
		super(_tsAgent);
		TsapiTrace.traceConstruction(this, LucentAgentImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentAgentImpl) {
			return tsAgent.equals(((LucentAgentImpl) obj).tsAgent);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentAgentImpl.class);
	}
}

