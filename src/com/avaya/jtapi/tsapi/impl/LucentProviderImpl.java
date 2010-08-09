package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import com.avaya.jtapi.tsapi.LucentProvider;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentProviderImpl extends TsapiProvider implements LucentProvider {
	LucentProviderImpl(String url, Vector<TsapiVendor> vendors) {
		super(url, vendors);
		TsapiTrace.traceEntry(
				"LucentProviderImpl[String url, Vector<TsapiVendor> vendors]",
				this);
		TsapiTrace.traceExit(
				"LucentProviderImpl[String url, Vector<TsapiVendor> vendors]",
				this);
	}

	LucentProviderImpl(TSProviderImpl _tsProvider) {
		super(_tsProvider);
		TsapiTrace.traceConstruction(this, LucentProviderImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentProviderImpl) {
			return tsProvider.equals(((LucentProviderImpl) obj).tsProvider);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentProviderImpl.class);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentProviderImpl JD-Core Version: 0.5.4
 */