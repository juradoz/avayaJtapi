package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class LucentTerminalImpl extends TsapiTerminal implements LucentTerminal {
	LucentTerminalImpl(final LucentProviderImpl _provider, final String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name, false);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	LucentTerminalImpl(final LucentProviderImpl _provider, final String _name,
			final boolean checkValidity) throws TsapiInvalidArgumentException {
		super(_provider, _name, checkValidity);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	LucentTerminalImpl(final TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	public LucentTerminalImpl(final TSProviderImpl _provider, final String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentTerminalImpl)
			return tsDevice.equals(((LucentTerminalImpl) obj).tsDevice);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentTerminalImpl.class);
	}
}
