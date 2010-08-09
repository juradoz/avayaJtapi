package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.ACDAddressEvent;
import javax.telephony.callcenter.ACDAddressListener;

public abstract class ACDAddressListenerAdapter extends AddressListenerAdapter
		implements ACDAddressListener {
	public void acdAddressBusy(ACDAddressEvent event) {
	}

	public void acdAddressLoggedOff(ACDAddressEvent event) {
	}

	public void acdAddressLoggedOn(ACDAddressEvent event) {
	}

	public void acdAddressNotReady(ACDAddressEvent event) {
	}

	public void acdAddressReady(ACDAddressEvent event) {
	}

	public void acdAddressUnknown(ACDAddressEvent event) {
	}

	public void acdAddressWorkNotReady(ACDAddressEvent event) {
	}

	public void acdAddressWorkReady(ACDAddressEvent event) {
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter JD-Core Version:
 * 0.5.4
 */