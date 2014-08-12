package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.ITsapiPeer;
import com.avaya.jtapi.tsapi.impl.core.AbstractTsapiPeer;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.Provider;

public class TsapiPeerImpl extends AbstractTsapiPeer implements ITsapiPeer {
	public TsapiPeerImpl() {
		TsapiTrace.traceConstruction(this, TsapiPeerImpl.class);
	}

	public String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		String name = getClass().getName();
		TsapiTrace.traceExit("getName[]", this);
		return name;
	}

	public final Provider getProvider(String providerString) {
		TsapiTrace.traceEntry("getProvider[String providerString]", this);

		TsapiProvider tsapiProvider = new TsapiProvider(providerString,
				this.vendors);
		this.vendors = null;
		Provider provider;
		if (tsapiProvider.tsProvider.isLucent()) {
			provider = (Provider) TsapiCreateObject.getTsapiObject(
					tsapiProvider.tsProvider, false);
		} else {
			provider = tsapiProvider;
		}
		TsapiTrace.traceExit("getProvider[String providerString]", this);
		return provider;
	}

	public void addVendor(String name, String versions) {
		TsapiTrace.traceEntry("addVendor[String name, String versions]", this);
		super.addVendor(name, versions);
		TsapiTrace.traceExit("addVendor[String name, String versions]", this);
	}

	public String[] getServices() {
		TsapiTrace.traceEntry("getServices[]", this);
		String[] services = super.getServices();
		TsapiTrace.traceExit("getServices[]", this);
		return services;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiPeerImpl.class);
	}
}