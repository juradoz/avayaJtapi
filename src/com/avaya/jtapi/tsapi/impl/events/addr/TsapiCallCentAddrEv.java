package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

public abstract class TsapiCallCentAddrEv extends TsapiAddressEvent {
	public TsapiCallCentAddrEv(Address _device, int _cause, int _metaCode,
			Object _privateData) {
		super(_device, _cause, _metaCode, _privateData, 2);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.addr.TsapiCallCentAddrEv JD-Core Version:
 * 0.5.4
 */