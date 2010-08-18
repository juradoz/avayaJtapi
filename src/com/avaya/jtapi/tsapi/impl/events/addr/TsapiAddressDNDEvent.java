package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcontrol.events.CallCtlAddrDoNotDisturbEv;

@SuppressWarnings("deprecation")
public final class TsapiAddressDNDEvent extends TsapiCallCtlAddressEvent
		implements CallCtlAddrDoNotDisturbEv {
	boolean state = false;

	public TsapiAddressDNDEvent(final Address _device, final boolean _state,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		state = _state;
	}

	public boolean getDoNotDisturbState() {
		return state;
	}

	public int getID() {
		return 200;
	}
}
