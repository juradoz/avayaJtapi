package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV5TerminalEx;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV5TerminalExImpl extends LucentV5TerminalImpl implements
		LucentV5TerminalEx {
	LucentV5TerminalExImpl(final LucentV5ProviderImpl _provider,
			final String _name) throws TsapiInvalidArgumentException {
		super(_provider, _name, false);
		TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
	}

	LucentV5TerminalExImpl(final LucentV5ProviderImpl _provider,
			final String _name, final boolean checkValidity)
			throws TsapiInvalidArgumentException {
		super(_provider, _name, checkValidity);
		TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
	}

	LucentV5TerminalExImpl(final TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
	}

	LucentV5TerminalExImpl(final TSProviderImpl _provider, final String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name);
		TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentV5TerminalExImpl)
			return tsDevice.equals(((LucentV5TerminalExImpl) obj).tsDevice);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5TerminalExImpl.class);
	}
}
