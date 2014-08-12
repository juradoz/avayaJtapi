package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Connection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV5ACDManagerConnectionImpl extends
		LucentACDManagerConnectionImpl implements LucentV5Connection {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV5ACDManagerConnectionImpl)) {
			return this.tsConnection
					.equals(((LucentV5ACDManagerConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	LucentV5ACDManagerConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this,
				LucentV5ACDManagerConnectionImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this,
				LucentV5ACDManagerConnectionImpl.class);
	}
}