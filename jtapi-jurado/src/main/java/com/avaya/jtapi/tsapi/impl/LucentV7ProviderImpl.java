package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentV7Provider;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.Vector;

class LucentV7ProviderImpl extends LucentV5ProviderImpl implements
		LucentV7Provider {
	public boolean equals(Object obj) {
		if ((obj instanceof LucentV7ProviderImpl)) {
			return this.tsProvider
					.equals(((LucentV7ProviderImpl) obj).tsProvider);
		}

		return false;
	}

	LucentV7ProviderImpl(String url, Vector<TsapiVendor> vendors) {
		super(url, vendors);
		TsapiTrace.traceConstruction(this, LucentV7ProviderImpl.class);
	}

	LucentV7ProviderImpl(TSProviderImpl _tsProvider) {
		super(_tsProvider);
		TsapiTrace.traceConstruction(this, LucentV7ProviderImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7ProviderImpl.class);
	}
}