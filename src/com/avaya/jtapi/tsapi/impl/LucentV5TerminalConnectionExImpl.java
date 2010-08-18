package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5TerminalConnectionEx;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class LucentV5TerminalConnectionExImpl extends
		LucentV5TerminalConnectionImpl implements LucentV5TerminalConnectionEx {
	LucentV5TerminalConnectionExImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this,
				LucentV5TerminalConnectionExImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5TerminalConnectionExImpl) {
			return tsConnection
					.equals(((LucentV5TerminalConnectionExImpl) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this,
				LucentV5TerminalConnectionExImpl.class);
	}
}

