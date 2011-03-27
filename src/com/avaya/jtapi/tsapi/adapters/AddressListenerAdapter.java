package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.AddressEvent;
import javax.telephony.AddressListener;

public abstract class AddressListenerAdapter implements AddressListener {
	@Override
	public void addressListenerEnded(final AddressEvent event) {
	}
}
