package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentConnectionImpl extends TsapiConnection implements LucentConnection {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentConnectionImpl)) {
			return this.tsConnection
					.equals(((LucentConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	LucentConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentConnectionImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentConnectionImpl.class);
	}
}