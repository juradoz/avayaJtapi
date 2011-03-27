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
			final Class implClass = Class
					.forName("com.avaya.jtapi.tsapi.impl.TsapiPeerImpl");
			impl = (ITsapiPeer) implClass.newInstance();
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void addVendor(final String vendorName, final String versions) {
		impl.addVendor(vendorName, versions);
	}

	@Override
	public String getName() {
		return super.getClass().getName();
	}

	@Override
	public Provider getProvider(final String providerString)
			throws ProviderUnavailableException {
		return impl.getProvider(providerString);
	}

	@Override
	public String[] getServices() {
		return impl.getServices();
	}
}
