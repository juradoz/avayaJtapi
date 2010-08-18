package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

import com.avaya.jtapi.tsapi.LucentAddressMsgWaitingEvent;

public final class LucentAddressMsgWaitingEventImpl extends
		TsapiAddressMsgWaitingEvent implements LucentAddressMsgWaitingEvent {
	public LucentAddressMsgWaitingEventImpl(Address _device, int _mwBits,
			int _cause, int _metaCode, Object _privateData) {
		super(_device, _mwBits, _cause, _metaCode, _privateData);
	}
}
