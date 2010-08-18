package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentACDManagerConnectionImpl extends TsapiACDManagerConnection
		implements LucentConnection {
	LucentACDManagerConnectionImpl(final TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace
				.traceConstruction(this, LucentACDManagerConnectionImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentACDManagerConnectionImpl)
			return tsConnection
					.equals(((LucentACDManagerConnectionImpl) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentACDManagerConnectionImpl.class);
	}
}
