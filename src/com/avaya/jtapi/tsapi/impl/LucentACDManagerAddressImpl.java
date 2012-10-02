package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentACDManagerAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class LucentACDManagerAddressImpl extends TsapiACDManagerAddress
		implements LucentACDManagerAddress {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentACDManagerAddressImpl)) {
			return this.tsDevice
					.equals(((LucentACDManagerAddressImpl) obj).tsDevice);
		}

		return false;
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

	LucentACDManagerAddressImpl(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentACDManagerAddressImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentACDManagerAddressImpl.class);
	}
}