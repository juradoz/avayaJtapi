package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV7Agent;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV7AgentImpl extends LucentV6AgentImpl implements
		LucentV7Agent {
	LucentV7AgentImpl(TSAgent _tsAgent) {
		super(_tsAgent);
		TsapiTrace.traceConstruction(this, LucentV7AgentImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV7AgentImpl) {
			return tsAgent.equals(((LucentV7AgentImpl) obj).tsAgent);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7AgentImpl.class);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentV7AgentImpl JD-Core Version: 0.5.4
 */