package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Connection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5ConnectionImpl extends LucentConnectionImpl implements
		LucentV5Connection {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV5ConnectionImpl)) {
			return this.tsConnection
					.equals(((LucentV5ConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	LucentV5ConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentV5ConnectionImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5ConnectionImpl.class);
	}
}