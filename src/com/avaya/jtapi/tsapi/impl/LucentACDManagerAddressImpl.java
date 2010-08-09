package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentACDManagerAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class LucentACDManagerAddressImpl extends TsapiACDManagerAddress
		implements LucentACDManagerAddress {
	LucentACDManagerAddressImpl(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentACDManagerAddressImpl.class);
	}

	public LucentACDManagerAddressImpl(TSProviderImpl tsProvider, String number)
			throws TsapiInvalidArgumentException {
		super(tsProvider, number);
		TsapiTrace
				.traceEntry(
						"LucentACDManagerAddressImpl[TSProviderImpl tsProvider, String number]",
						this);
		TsapiTrace
				.traceExit(
						"LucentACDManagerAddressImpl[TSProviderImpl tsProvider, String number]",
						this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentACDManagerAddressImpl) {
			return tsDevice
					.equals(((LucentACDManagerAddressImpl) obj).tsDevice);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentACDManagerAddressImpl.class);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentACDManagerAddressImpl JD-Core Version: 0.5.4
 */