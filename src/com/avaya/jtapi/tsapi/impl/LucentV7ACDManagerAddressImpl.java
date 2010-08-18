package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV7ACDManagerAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentV7ACDManagerAddressImpl extends LucentACDManagerAddressImpl
		implements LucentV7ACDManagerAddress {
	LucentV7ACDManagerAddressImpl(final TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentV7ACDManagerAddressImpl.class);
	}

	LucentV7ACDManagerAddressImpl(final TSProviderImpl tsProvider,
			final String number) throws TsapiInvalidArgumentException {
		super(tsProvider, number);
		TsapiTrace.traceConstruction(this, LucentV7ACDManagerAddressImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentV7ACDManagerAddressImpl)
			return tsDevice
					.equals(((LucentV7ACDManagerAddressImpl) obj).tsDevice);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7ACDManagerAddressImpl.class);
	}
}
