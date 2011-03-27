package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.ACDAddressEvent;
import javax.telephony.callcenter.ACDAddressListener;

public abstract class ACDAddressListenerAdapter extends AddressListenerAdapter
		implements ACDAddressListener {
	@Override
	public void acdAddressBusy(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressLoggedOff(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressLoggedOn(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressNotReady(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressReady(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressUnknown(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressWorkNotReady(final ACDAddressEvent event) {
	}

	@Override
	public void acdAddressWorkReady(final ACDAddressEvent event) {
	}
}
