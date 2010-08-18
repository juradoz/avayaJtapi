package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentTerminalConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentTerminalConnectionImpl extends TsapiTerminalConnection implements
		LucentTerminalConnection {
	LucentTerminalConnectionImpl(final TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentTerminalConnectionImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentTerminalConnectionImpl)
			return tsConnection
					.equals(((LucentTerminalConnectionImpl) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentTerminalConnectionImpl.class);
	}
}
