package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlAddressEvent;
import javax.telephony.callcontrol.CallControlAddressListener;

public abstract class CallControlAddressListenerAdapter extends
		AddressListenerAdapter implements CallControlAddressListener {
	@Override
	public void addressDoNotDisturb(final CallControlAddressEvent event) {
	}

	@Override
	public void addressForwarded(final CallControlAddressEvent event) {
	}

	@Override
	public void addressMessageWaiting(final CallControlAddressEvent event) {
	}
}
