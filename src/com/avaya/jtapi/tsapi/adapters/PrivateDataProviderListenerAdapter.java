package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataEvent;
import javax.telephony.privatedata.PrivateDataProviderListener;

public abstract class PrivateDataProviderListenerAdapter extends
		ProviderListenerAdapter implements PrivateDataProviderListener {
	@Override
	public void providerPrivateData(final PrivateDataEvent event) {
	}
}
