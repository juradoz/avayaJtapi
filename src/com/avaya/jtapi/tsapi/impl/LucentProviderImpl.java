package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import com.avaya.jtapi.tsapi.LucentProvider;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentProviderImpl extends TsapiProvider implements LucentProvider {
	LucentProviderImpl(final String url, final Vector<TsapiVendor> vendors) {
		super(url, vendors);
		TsapiTrace.traceEntry(
				"LucentProviderImpl[String url, Vector<TsapiVendor> vendors]",
				this);
		TsapiTrace.traceExit(
				"LucentProviderImpl[String url, Vector<TsapiVendor> vendors]",
				this);
	}

	LucentProviderImpl(final TSProviderImpl _tsProvider) {
		super(_tsProvider);
		TsapiTrace.traceConstruction(this, LucentProviderImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LucentProviderImpl)
			return tsProvider.equals(((LucentProviderImpl) obj).tsProvider);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentProviderImpl.class);
	}
}
