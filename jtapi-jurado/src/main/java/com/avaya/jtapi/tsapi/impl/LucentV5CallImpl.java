package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Call;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5CallImpl extends LucentCallEx2Impl implements LucentV5Call {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV5CallImpl)) {
			this.tsCall = this.tsCall.getHandOff();
			return this.tsCall.equals(((LucentV5CallImpl) obj).tsCall);
		}

		return false;
	}

	LucentV5CallImpl(LucentV5ProviderImpl _provider) {
		super(_provider, 0);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(LucentV5ProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(LucentV5ProviderImpl _provider, int callID) {
		super(_provider, callID);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(TSProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	LucentV5CallImpl(TSCall _tscall) {
		super(_tscall);
		TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5CallImpl.class);
	}
}