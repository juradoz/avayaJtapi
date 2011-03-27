package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import javax.telephony.callcenter.ACDConnection;
import javax.telephony.callcenter.ACDManagerConnection;

import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiACDManagerConnection extends TsapiConnection implements
		ACDManagerConnection {
	TsapiACDManagerConnection(final TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, TsapiACDManagerConnection.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiACDManagerConnection)
			return tsConnection
					.equals(((TsapiACDManagerConnection) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDManagerConnection.class);
	}

	// ERROR //
	@Override
	public final javax.telephony.callcenter.ACDConnection[] getACDConnections()
			throws com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException {
		try {
			Vector<TSConnection> tsconn = null;
			tsconn = tsConnection.getACDConns();
			if (tsconn == null) {
				privData = null;
				return null;
			}
			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					privData = null;
					return null;
				}
				final ACDConnection[] tsapiConn = new ACDConnection[tsconn
						.size()];
				for (int i = 0; i < tsconn.size(); ++i)
					tsapiConn[i] = (ACDConnection) TsapiCreateObject
							.getTsapiObject(tsconn.elementAt(i), true);
				privData = null;
				return tsapiConn;
			}
		} finally {
			privData = null;
		}
	}
}
