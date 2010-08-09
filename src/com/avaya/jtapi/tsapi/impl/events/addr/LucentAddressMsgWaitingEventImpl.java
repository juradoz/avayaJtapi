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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.addr.LucentAddressMsgWaitingEventImpl
 * JD-Core Version: 0.5.4
 */