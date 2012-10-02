package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Connection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV5ACDConnectionImpl extends LucentACDConnectionImpl implements
		LucentV5Connection {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV5ACDConnectionImpl)) {
			return this.tsConnection
					.equals(((LucentV5ACDConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	LucentV5ACDConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentV5ACDConnectionImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5ACDConnectionImpl.class);
	}
}