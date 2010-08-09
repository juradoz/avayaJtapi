package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

import com.avaya.jtapi.tsapi.ITsapiAddressMsgWaitingEvent;

public class TsapiAddressMsgWaitingEvent extends TsapiCallCtlAddressEvent
		implements ITsapiAddressMsgWaitingEvent {
	int mwBits = 0;

	public TsapiAddressMsgWaitingEvent(Address _device, int _mwBits,
			int _cause, int _metaCode, Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		mwBits = _mwBits;
	}

	public final int getID() {
		return 202;
	}

	public final int getMessageWaitingBits() {
		return mwBits;
	}

	public final boolean getMessageWaitingState() {
		return mwBits != 0;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressMsgWaitingEvent JD-Core
 * Version: 0.5.4
 */