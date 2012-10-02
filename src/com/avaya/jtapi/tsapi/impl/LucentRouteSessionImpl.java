package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.LucentRouteSession;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentRouteSessionImpl extends TsapiRouteSession implements
		LucentRouteSession, LucentCallInfo {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentRouteSessionImpl)) {
			return this.tsRouteSession
					.equals(((LucentRouteSessionImpl) obj).tsRouteSession);
		}

		return false;
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		TsapiTrace.traceEntry("getDeviceHistory[]", this);
		V7DeviceHistoryEntry[] history = this.tsRouteSession.getDeviceHistory();
		TsapiTrace.traceExit("getDeviceHistory[]", this);
		return history;
	}

	LucentRouteSessionImpl(TSRouteSession _tsRouteSession) {
		super(_tsRouteSession);
		TsapiTrace.traceConstruction(this, LucentRouteSessionImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentRouteSessionImpl.class);
	}
}