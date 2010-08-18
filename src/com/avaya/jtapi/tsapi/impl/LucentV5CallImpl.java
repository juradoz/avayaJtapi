package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Call;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5CallImpl extends LucentCallEx2Impl implements LucentV5Call {
	LucentV5CallImpl(final LucentV5ProviderImpl _provider) {
		super(_provider, 0);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(final LucentV5ProviderImpl _provider,
			final CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(final LucentV5ProviderImpl _provider, final int callID) {
		super(_provider, callID);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(final TSCall _tscall) {
		super(_tscall);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(final TSProviderImpl _provider,
			final CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentV5CallImpl) {
			tsCall = tsCall.getHandOff();
			return tsCall.equals(((LucentV5CallImpl) obj).tsCall);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5CallImpl.class);
	}
}
