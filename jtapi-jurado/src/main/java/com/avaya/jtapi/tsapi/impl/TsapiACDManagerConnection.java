package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.Vector;
import javax.telephony.callcenter.ACDConnection;
import javax.telephony.callcenter.ACDManagerConnection;

class TsapiACDManagerConnection extends TsapiConnection implements
		ACDManagerConnection {
	public final ACDConnection[] getACDConnections()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDConnections[]", this);
		try {
			Vector<?> tsconn = this.tsConnection.getACDConns();
			if (tsconn == null) {
				TsapiTrace.traceExit("getACDConnections[]", this);
				return null;
			}
			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					TsapiTrace.traceExit("getACDConnections[]", this);
					return null;
				}
				ACDConnection[] tsapiConn = new ACDConnection[tsconn.size()];
				for (int i = 0; i < tsconn.size(); i++) {
					tsapiConn[i] = ((ACDConnection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									true));
				}
				TsapiTrace.traceExit("getACDConnections[]", this);
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiACDManagerConnection)) {
			return this.tsConnection
					.equals(((TsapiACDManagerConnection) obj).tsConnection);
		}

		return false;
	}

	TsapiACDManagerConnection(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this, TsapiACDManagerConnection.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDManagerConnection.class);
	}
}