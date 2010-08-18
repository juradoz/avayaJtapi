package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

import com.avaya.jtapi.tsapi.LucentAddressMsgWaitingEvent;

public final class LucentAddressMsgWaitingEventImpl extends
		TsapiAddressMsgWaitingEvent implements LucentAddressMsgWaitingEvent {
	public LucentAddressMsgWaitingEventImpl(final Address _device,
			final int _mwBits, final int _cause, final int _metaCode,
			final Object _privateData) {
		super(_device, _mwBits, _cause, _metaCode, _privateData);
	}
}
