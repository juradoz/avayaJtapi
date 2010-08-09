package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class LucentTerminalImpl extends TsapiTerminal implements LucentTerminal {
	LucentTerminalImpl(LucentProviderImpl _provider, String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name, false);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	LucentTerminalImpl(LucentProviderImpl _provider, String _name,
			boolean checkValidity) throws TsapiInvalidArgumentException {
		super(_provider, _name, checkValidity);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	LucentTerminalImpl(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	public LucentTerminalImpl(TSProviderImpl _provider, String _name)
			throws TsapiInvalidArgumentException {
		super(_provider, _name);
		TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentTerminalImpl) {
			return tsDevice.equals(((LucentTerminalImpl) obj).tsDevice);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentTerminalImpl.class);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentTerminalImpl JD-Core Version: 0.5.4
 */