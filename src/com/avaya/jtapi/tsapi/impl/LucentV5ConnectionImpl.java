package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Connection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5ConnectionImpl extends LucentConnectionImpl implements
		LucentV5Connection {
	LucentV5ConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentV5ConnectionImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5ConnectionImpl) {
			return tsConnection
					.equals(((LucentV5ConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5ConnectionImpl.class);
	}
}

