package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV7Call;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class LucentV7CallImpl extends LucentV5CallImpl implements
		LucentV7Call {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV7CallImpl)) {
			this.tsCall = this.tsCall.getHandOff();
			return this.tsCall.equals(((LucentV7CallImpl) obj).tsCall);
		}

		return false;
	}

	LucentV7CallImpl(LucentV7ProviderImpl _provider) {
		super(_provider, 0);
		TsapiTrace.traceConstruction(this, LucentV7CallImpl.class);
	}

	LucentV7CallImpl(LucentV7ProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentV7CallImpl.class);
	}

	LucentV7CallImpl(LucentV7ProviderImpl _provider, int callID) {
		super(_provider, callID);
		TsapiTrace.traceConstruction(this, LucentV7CallImpl.class);
	}

	public LucentV7CallImpl(TSProviderImpl _provider, CSTAConnectionID connID) {
		super(_provider, connID);
		TsapiTrace.traceConstruction(this, LucentV7CallImpl.class);
	}

	LucentV7CallImpl(TSCall _tscall) {
		super(_tscall);
		TsapiTrace.traceConstruction(this, LucentV7CallImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7CallImpl.class);
	}
}