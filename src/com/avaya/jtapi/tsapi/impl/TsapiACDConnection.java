package com.avaya.jtapi.tsapi.impl;

import javax.telephony.callcenter.ACDConnection;
import javax.telephony.callcenter.ACDManagerConnection;

import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiACDConnection extends TsapiConnection implements ACDConnection {
	TsapiACDConnection(final TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, TsapiACDConnection.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiACDConnection)
			return tsConnection.equals(((TsapiACDConnection) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDConnection.class);
	}

	@Override
	public final ACDManagerConnection getACDManagerConnection()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDManagerConnection[]", this);
		try {
			final TSConnection tsConn = tsConnection.getACDManagerConn();
			if (tsConn == null) {
				TsapiTrace.traceExit("getACDManagerConnection[]", this);
				return null;
			}

			final ACDManagerConnection conn = (ACDManagerConnection) TsapiCreateObject
					.getTsapiObject(tsConn, true);
			TsapiTrace.traceExit("getACDManagerConnection[]", this);
			return conn;
		} finally {
			privData = null;
		}
	}
}
