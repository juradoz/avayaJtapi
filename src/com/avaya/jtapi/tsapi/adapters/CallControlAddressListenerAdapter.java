package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlAddressEvent;
import javax.telephony.callcontrol.CallControlAddressListener;

public abstract class CallControlAddressListenerAdapter extends
		AddressListenerAdapter implements CallControlAddressListener {
	public void addressDoNotDisturb(CallControlAddressEvent event) {
	}

	public void addressForwarded(CallControlAddressEvent event) {
	}

	public void addressMessageWaiting(CallControlAddressEvent event) {
	}
}

