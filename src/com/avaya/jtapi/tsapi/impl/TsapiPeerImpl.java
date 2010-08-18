package com.avaya.jtapi.tsapi.impl;

import javax.telephony.Provider;

import com.avaya.jtapi.tsapi.ITsapiPeer;
import com.avaya.jtapi.tsapi.impl.core.AbstractTsapiPeer;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class TsapiPeerImpl extends AbstractTsapiPeer implements ITsapiPeer {
	public TsapiPeerImpl() {
		TsapiTrace.traceConstruction(this, TsapiPeerImpl.class);
	}

	@Override
	public void addVendor(final String name, final String versions) {
		TsapiTrace.traceEntry("addVendor[String name, String versions]", this);
		super.addVendor(name, versions);
		TsapiTrace.traceExit("addVendor[String name, String versions]", this);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiPeerImpl.class);
	}

	public String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		final String name = super.getClass().getName();
		TsapiTrace.traceExit("getName[]", this);
		return name;
	}

	public final Provider getProvider(final String providerString) {
		TsapiTrace.traceEntry("getProvider[String providerString]", this);

		final TsapiProvider tsapiProvider = new TsapiProvider(providerString,
				vendors);
		vendors = null;
		Provider provider;
		if (tsapiProvider.tsProvider.isLucent())
			provider = (Provider) TsapiCreateObject.getTsapiObject(
					tsapiProvider.tsProvider, false);
		else
			provider = tsapiProvider;
		TsapiTrace.traceExit("getProvider[String providerString]", this);
		return provider;
	}

	@Override
	public String[] getServices() {
		TsapiTrace.traceEntry("getServices[]", this);
		final String[] services = super.getServices();
		TsapiTrace.traceExit("getServices[]", this);
		return services;
	}
}
