package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV7ACDManagerAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV7ACDManagerAddressImpl extends LucentACDManagerAddressImpl
		implements LucentV7ACDManagerAddress {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV7ACDManagerAddressImpl)) {
			return this.tsDevice
					.equals(((LucentV7ACDManagerAddressImpl) obj).tsDevice);
		}

		return false;
	}

	LucentV7ACDManagerAddressImpl(TSProviderImpl tsProvider, String number)
			throws TsapiInvalidArgumentException {
		super(tsProvider, number);
		TsapiTrace.traceConstruction(this, LucentV7ACDManagerAddressImpl.class);
	}

	LucentV7ACDManagerAddressImpl(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentV7ACDManagerAddressImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7ACDManagerAddressImpl.class);
	}
}