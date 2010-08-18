package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.AddressEvent;
import javax.telephony.AddressListener;

public abstract class AddressListenerAdapter implements AddressListener {
	public void addressListenerEnded(final AddressEvent event) {
	}
}
