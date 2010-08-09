package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.LucentRouteSession;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentRouteSessionImpl extends TsapiRouteSession implements
		LucentRouteSession, LucentCallInfo {
	LucentRouteSessionImpl(TSRouteSession _tsRouteSession) {
		super(_tsRouteSession);
		TsapiTrace.traceConstruction(this, LucentRouteSessionImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentRouteSessionImpl) {
			return tsRouteSession
					.equals(((LucentRouteSessionImpl) obj).tsRouteSession);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentRouteSessionImpl.class);
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		TsapiTrace.traceEntry("getDeviceHistory[]", this);
		V7DeviceHistoryEntry[] history = tsRouteSession.getDeviceHistory();
		TsapiTrace.traceExit("getDeviceHistory[]", this);
		return history;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentRouteSessionImpl JD-Core Version: 0.5.4
 */