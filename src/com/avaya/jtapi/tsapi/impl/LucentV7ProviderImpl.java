package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import com.avaya.jtapi.tsapi.LucentV7Provider;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class LucentV7ProviderImpl extends LucentV5ProviderImpl implements
		LucentV7Provider {
	LucentV7ProviderImpl(String url, Vector<TsapiVendor> vendors) {
		super(url, vendors);
		TsapiTrace.traceConstruction(this, LucentV7ProviderImpl.class);
	}

	LucentV7ProviderImpl(TSProviderImpl _tsProvider) {
		super(_tsProvider);
		TsapiTrace.traceConstruction(this, LucentV7ProviderImpl.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LucentV7ProviderImpl) {
			return tsProvider.equals(((LucentV7ProviderImpl) obj).tsProvider);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, LucentV7ProviderImpl.class);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.LucentV7ProviderImpl JD-Core Version: 0.5.4
 */