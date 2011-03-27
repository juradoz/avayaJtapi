package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataAddressListener;
import javax.telephony.privatedata.PrivateDataEvent;

public abstract class PrivateDataAddressListenerAdapter extends
		AddressListenerAdapter implements PrivateDataAddressListener {
	@Override
	public void addressPrivateData(final PrivateDataEvent event) {
	}
}
