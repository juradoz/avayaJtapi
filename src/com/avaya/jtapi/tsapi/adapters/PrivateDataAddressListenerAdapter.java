package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataAddressListener;
import javax.telephony.privatedata.PrivateDataEvent;

public abstract class PrivateDataAddressListenerAdapter extends
		AddressListenerAdapter implements PrivateDataAddressListener {
	public void addressPrivateData(PrivateDataEvent event) {
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.adapters.PrivateDataAddressListenerAdapter JD-Core
 * Version: 0.5.4
 */