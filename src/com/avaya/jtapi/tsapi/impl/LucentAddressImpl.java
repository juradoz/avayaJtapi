package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class LucentAddressImpl extends TsapiAddress implements LucentAddress {
	LucentAddressImpl(final TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
	}

	LucentAddressImpl(final TSProviderImpl TSProviderImpl,
			final CSTAExtendedDeviceID deviceID)
			throws TsapiInvalidArgumentException {
		super(TSProviderImpl, deviceID);
		TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
	}

	LucentAddressImpl(final TSProviderImpl TSProviderImpl, final String number)
			throws TsapiInvalidArgumentException {
		super(TSProviderImpl, number);
		TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentAddressImpl)
			return tsDevice.equals(((LucentAddressImpl) obj).tsDevice);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentAddressImpl.class);
	}
}
