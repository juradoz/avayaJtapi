package com.avaya.jtapi.tsapi.impl;

import javax.telephony.callcenter.ACDConnection;
import javax.telephony.callcenter.ACDManagerConnection;

import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiACDConnection extends TsapiConnection implements ACDConnection {
	TsapiACDConnection(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, TsapiACDConnection.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiACDConnection) {
			return tsConnection.equals(((TsapiACDConnection) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDConnection.class);
	}

	public final ACDManagerConnection getACDManagerConnection()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDManagerConnection[]", this);
		try {
			TSConnection tsConn = tsConnection.getACDManagerConn();
			if (tsConn == null) {
				TsapiTrace.traceExit("getACDManagerConnection[]", this);
				return null;
			}

			ACDManagerConnection conn = (ACDManagerConnection) TsapiCreateObject
					.getTsapiObject(tsConn, true);
			TsapiTrace.traceExit("getACDManagerConnection[]", this);
			return conn;
		} finally {
			privData = null;
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiACDConnection JD-Core Version: 0.5.4
 */