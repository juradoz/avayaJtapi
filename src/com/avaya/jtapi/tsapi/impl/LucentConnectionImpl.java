package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentConnectionImpl extends TsapiConnection implements LucentConnection {
	LucentConnectionImpl(final TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentConnectionImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentConnectionImpl)
			return tsConnection
					.equals(((LucentConnectionImpl) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentConnectionImpl.class);
	}
}
