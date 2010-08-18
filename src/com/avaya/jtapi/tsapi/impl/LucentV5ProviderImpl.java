package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import com.avaya.jtapi.tsapi.LucentV5Provider;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV5ProviderImpl extends LucentProviderImpl implements
		LucentV5Provider {
	LucentV5ProviderImpl(final String url, final Vector<TsapiVendor> vendors) {
		super(url, vendors);
		TsapiTrace.traceConstruction(this, LucentV5ProviderImpl.class);
	}

	LucentV5ProviderImpl(final TSProviderImpl _tsProvider) {
		super(_tsProvider);
		TsapiTrace.traceConstruction(this, LucentV5ProviderImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentV5ProviderImpl)
			return tsProvider.equals(((LucentV5ProviderImpl) obj).tsProvider);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV5ProviderImpl.class);
	}
}
