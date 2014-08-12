package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.callcenter.ACDConnection;
import javax.telephony.callcenter.ACDManagerConnection;

class TsapiACDConnection extends TsapiConnection implements ACDConnection {
	public final ACDManagerConnection getACDManagerConnection()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDManagerConnection[]", this);
		try {
			TSConnection tsConn = this.tsConnection.getACDManagerConn();
			if (tsConn == null) {
				TsapiTrace.traceExit("getACDManagerConnection[]", this);
				return null;
			}

			ACDManagerConnection conn = (ACDManagerConnection) TsapiCreateObject
					.getTsapiObject(tsConn, true);
			TsapiTrace.traceExit("getACDManagerConnection[]", this);
			return conn;
		} finally {
			this.privData = null;
		}
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiACDConnection)) {
			return this.tsConnection
					.equals(((TsapiACDConnection) obj).tsConnection);
		}

		return false;
	}

	TsapiACDConnection(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, TsapiACDConnection.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDConnection.class);
	}
}