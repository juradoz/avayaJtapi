package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentCallEx;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentCallExImpl extends LucentCallImpl implements LucentCallEx {
	LucentCallExImpl(LucentProviderImpl _provider) {
		super(_provider, 0);
		TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
	}

	LucentCallExImpl(LucentProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
	}

	LucentCallExImpl(LucentProviderImpl _provider, int callID) {
		super(_provider, callID);
		TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
	}

	LucentCallExImpl(TSCall _tscall) {
		super(_tscall);
		TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
	}

	LucentCallExImpl(TSProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentCallExImpl) {
			tsCall = tsCall.getHandOff();
			return tsCall.equals(((LucentCallExImpl) obj).tsCall);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentCallExImpl.class);
	}
}

