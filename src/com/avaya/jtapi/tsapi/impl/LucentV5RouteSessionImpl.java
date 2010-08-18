package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentRouteSession;
import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV5RouteSessionImpl extends LucentRouteSessionImpl implements
		LucentRouteSession, LucentV5CallInfo {
	LucentV5RouteSessionImpl(TSRouteSession _tsRouteSession) {
		super(_tsRouteSession);
		TsapiTrace.traceConstruction(this, LucentV5RouteSessionImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5RouteSessionImpl) {
			return tsRouteSession
					.equals(((LucentV5RouteSessionImpl) obj).tsRouteSession);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5RouteSessionImpl.class);
	}
}

