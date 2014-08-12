package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcontrol.events.CallCtlTermDoNotDisturbEv;

@SuppressWarnings("deprecation")
public final class TsapiTerminalDNDEvent extends TsapiCallCtlTerminalEvent
		implements CallCtlTermDoNotDisturbEv {
	private boolean state = false;

	public boolean getDoNotDisturbState() {
		return this.state;
	}

	public int getID() {
		return 221;
	}

	public TsapiTerminalDNDEvent(Terminal _device, boolean _state, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		this.state = _state;
	}
}