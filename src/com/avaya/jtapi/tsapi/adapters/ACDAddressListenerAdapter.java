package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.ACDAddressEvent;
import javax.telephony.callcenter.ACDAddressListener;

public abstract class ACDAddressListenerAdapter extends AddressListenerAdapter
		implements ACDAddressListener {
	public void acdAddressBusy(final ACDAddressEvent event) {
	}

	public void acdAddressLoggedOff(final ACDAddressEvent event) {
	}

	public void acdAddressLoggedOn(final ACDAddressEvent event) {
	}

	public void acdAddressNotReady(final ACDAddressEvent event) {
	}

	public void acdAddressReady(final ACDAddressEvent event) {
	}

	public void acdAddressUnknown(final ACDAddressEvent event) {
	}

	public void acdAddressWorkNotReady(final ACDAddressEvent event) {
	}

	public void acdAddressWorkReady(final ACDAddressEvent event) {
	}
}
