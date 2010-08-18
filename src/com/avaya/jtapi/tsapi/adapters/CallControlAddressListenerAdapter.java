package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlAddressEvent;
import javax.telephony.callcontrol.CallControlAddressListener;

public abstract class CallControlAddressListenerAdapter extends
		AddressListenerAdapter implements CallControlAddressListener {
	public void addressDoNotDisturb(final CallControlAddressEvent event) {
	}

	public void addressForwarded(final CallControlAddressEvent event) {
	}

	public void addressMessageWaiting(final CallControlAddressEvent event) {
	}
}
