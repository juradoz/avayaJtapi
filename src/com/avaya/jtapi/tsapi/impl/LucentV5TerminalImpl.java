package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5Terminal;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5TerminalImpl extends LucentTerminalImpl implements
		LucentV5Terminal {
	LucentV5TerminalImpl(LucentV5ProviderImpl _provider, String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name, false);
		TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
	}

	LucentV5TerminalImpl(LucentV5ProviderImpl _provider, String _name,
			boolean checkValidity) throws TsapiInvalidArgumentException {
		super(_provider, _name, checkValidity);
		TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
	}

	LucentV5TerminalImpl(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
	}

	LucentV5TerminalImpl(TSProviderImpl _provider, String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name);
		TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV5TerminalImpl) {
			return tsDevice.equals(((LucentV5TerminalImpl) obj).tsDevice);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5TerminalImpl.class);
	}
}

