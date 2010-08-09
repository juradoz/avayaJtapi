package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Connection;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV5ACDManagerConnectionImpl extends
		LucentACDManagerConnectionImpl implements LucentV5Connection {
	LucentV5ACDManagerConnectionImpl(TSConnection _tsConnection) {
		super(_tsConnection);
		TsapiTrace.traceConstruction(this,
				LucentV5ACDManagerConnectionImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5ACDManagerConnectionImpl) {
			return tsConnection
					.equals(((LucentV5ACDManagerConnectionImpl) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this,
				LucentV5ACDManagerConnectionImpl.class);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentV5ACDManagerConnectionImpl JD-Core Version:
 * 0.5.4
 */