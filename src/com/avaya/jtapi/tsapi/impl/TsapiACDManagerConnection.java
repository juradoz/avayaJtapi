package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import javax.telephony.callcenter.ACDConnection;
import javax.telephony.callcenter.ACDManagerConnection;

import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiACDManagerConnection extends TsapiConnection implements
		ACDManagerConnection {
	TsapiACDManagerConnection(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, TsapiACDManagerConnection.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiACDManagerConnection) {
			return tsConnection
					.equals(((TsapiACDManagerConnection) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDManagerConnection.class);
	}

	// ERROR //
	public final javax.telephony.callcenter.ACDConnection[] getACDConnections()
			throws com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException {
		try {
			Vector<TSConnection> tsconn = null;
			tsconn = this.tsConnection.getACDConns();
			if (tsconn == null) {
				this.privData = null;
				return null;
			}
			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					this.privData = null;
					return null;
				}
				ACDConnection[] tsapiConn = new ACDConnection[tsconn.size()];
				for (int i = 0; i < tsconn.size(); ++i) {
					tsapiConn[i] = ((ACDConnection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									true));
				}
				this.privData = null;
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiACDManagerConnection JD-Core Version: 0.5.4
 */