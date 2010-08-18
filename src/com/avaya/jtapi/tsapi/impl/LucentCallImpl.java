package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class LucentCallImpl extends TsapiCall implements LucentCall {
	LucentCallImpl(LucentProviderImpl _provider) {
		super(_provider, 0);
		TsapiTrace.traceConstruction(this, LucentCallImpl.class);
	}

	LucentCallImpl(LucentProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentCallImpl.class);
	}

	LucentCallImpl(LucentProviderImpl _provider, int callID) {
		super(_provider, callID);
		TsapiTrace.traceConstruction(this, LucentCallImpl.class);
	}

	LucentCallImpl(TSCall _tscall) {
		super(_tscall);
		TsapiTrace.traceConstruction(this, LucentCallImpl.class);
	}

	public LucentCallImpl(TSProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentCallImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentCallImpl) {
			tsCall = tsCall.getHandOff();
			return tsCall.equals(((LucentCallImpl) obj).tsCall);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentCallImpl.class);
	}
}

