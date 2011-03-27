package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

import com.avaya.jtapi.tsapi.ITsapiAddressMsgWaitingEvent;

public class TsapiAddressMsgWaitingEvent extends TsapiCallCtlAddressEvent
		implements ITsapiAddressMsgWaitingEvent {
	int mwBits = 0;

	public TsapiAddressMsgWaitingEvent(final Address _device,
			final int _mwBits, final int _cause, final int _metaCode,
			final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		mwBits = _mwBits;
	}

	@Override
	public final int getID() {
		return 202;
	}

	public final int getMessageWaitingBits() {
		return mwBits;
	}

	@Override
	public final boolean getMessageWaitingState() {
		return mwBits != 0;
	}
}
