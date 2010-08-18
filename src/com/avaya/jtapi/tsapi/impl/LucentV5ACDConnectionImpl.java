package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Connection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV5ACDConnectionImpl extends LucentACDConnectionImpl implements
		LucentV5Connection {
	LucentV5ACDConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentV5ACDConnectionImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5ACDConnectionImpl) {
			return tsConnection
					.equals(((LucentV5ACDConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5ACDConnectionImpl.class);
	}
}

