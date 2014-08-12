package com.avaya.jtapi.tsapi;

import javax.telephony.Provider;
import javax.telephony.ProviderUnavailableException;
import org.apache.log4j.Logger;

public class TsapiPeer implements ITsapiPeer {
	public static final String VERSION = "6.2.0.54";
	ITsapiPeer impl = null;
	Logger logger = Logger.getLogger(TsapiPeer.class);

	public TsapiPeer() {
		try {
			Class<?> implClass = Class
					.forName("com.avaya.jtapi.tsapi.impl.TsapiPeerImpl");
			this.impl = ((ITsapiPeer) implClass.newInstance());
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
	}

	public void addVendor(String vendorName, String versions) {
		this.impl.addVendor(vendorName, versions);
	}

	public String getName() {
		return getClass().getName();
	}

	public Provider getProvider(String providerString)
			throws ProviderUnavailableException {
		return this.impl.getProvider(providerString);
	}

	public String[] getServices() {
		return this.impl.getServices();
	}
}