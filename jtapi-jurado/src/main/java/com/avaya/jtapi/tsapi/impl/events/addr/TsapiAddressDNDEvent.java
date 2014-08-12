package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcontrol.events.CallCtlAddrDoNotDisturbEv;

@SuppressWarnings("deprecation")
public final class TsapiAddressDNDEvent extends TsapiCallCtlAddressEvent
		implements CallCtlAddrDoNotDisturbEv {
	boolean state = false;

	public boolean getDoNotDisturbState() {
		return this.state;
	}

	public int getID() {
		return 200;
	}

	public TsapiAddressDNDEvent(Address _device, boolean _state, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		this.state = _state;
	}
}