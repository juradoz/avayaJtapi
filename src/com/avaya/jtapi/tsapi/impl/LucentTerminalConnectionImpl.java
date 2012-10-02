package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentTerminalConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentTerminalConnectionImpl extends TsapiTerminalConnection implements
		LucentTerminalConnection {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentTerminalConnectionImpl)) {
			return this.tsConnection
					.equals(((LucentTerminalConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	LucentTerminalConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, LucentTerminalConnectionImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentTerminalConnectionImpl.class);
	}
}