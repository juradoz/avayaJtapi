package com.avaya.jtapi.tsapi;

import javax.telephony.Provider;
import javax.telephony.ProviderUnavailableException;

import org.apache.log4j.Logger;

public class TsapiPeer implements ITsapiPeer {
	public static final String VERSION = "5.2.0.483";
	ITsapiPeer impl = null;
	Logger logger = Logger.getLogger(TsapiPeer.class);

	@SuppressWarnings("unchecked")
	public TsapiPeer() {
		try {
			Class implClass = Class
					.forName("com.avaya.jtapi.tsapi.impl.TsapiPeerImpl");
			impl = ((ITsapiPeer) implClass.newInstance());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void addVendor(String vendorName, String versions) {
		impl.addVendor(vendorName, versions);
	}

	public String getName() {
		return super.getClass().getName();
	}

	public Provider getProvider(String providerString)
			throws ProviderUnavailableException {
		return impl.getProvider(providerString);
	}

	public String[] getServices() {
		return impl.getServices();
	}
}

