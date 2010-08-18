package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5TerminalConnection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5TerminalConnectionImpl extends LucentTerminalConnectionImpl
		implements LucentV5TerminalConnection {
	LucentV5TerminalConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace
				.traceConstruction(this, LucentV5TerminalConnectionImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5TerminalConnectionImpl) {
			return tsConnection
					.equals(((LucentV5TerminalConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5TerminalConnectionImpl.class);
	}
}

