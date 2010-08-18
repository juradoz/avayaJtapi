package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentCallEx2;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentCallEx2Impl extends LucentCallExImpl implements LucentCallEx2 {
	LucentCallEx2Impl(final LucentProviderImpl _provider) {
		super(_provider, 0);
		TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
	}

	LucentCallEx2Impl(final LucentProviderImpl _provider,
			final CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
	}

	LucentCallEx2Impl(final LucentProviderImpl _provider, final int callID) {
		super(_provider, callID);
		TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
	}

	LucentCallEx2Impl(final TSCall _tscall) {
		super(_tscall);
		TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
	}

	LucentCallEx2Impl(final TSProviderImpl _provider,
			final CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentCallEx2Impl) {
			tsCall = tsCall.getHandOff();
			return tsCall.equals(((LucentCallEx2Impl) obj).tsCall);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentCallEx2Impl.class);
	}
}
