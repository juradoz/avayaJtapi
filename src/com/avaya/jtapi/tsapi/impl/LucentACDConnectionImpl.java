package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentACDConnectionImpl extends TsapiACDConnection implements
		LucentConnection {
	LucentACDConnectionImpl(final TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentACDConnectionImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentACDConnectionImpl)
			return tsConnection
					.equals(((LucentACDConnectionImpl) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentACDConnectionImpl.class);
	}
}
