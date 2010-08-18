package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcontrol.events.CallCtlTermDoNotDisturbEv;

@SuppressWarnings("deprecation")
public final class TsapiTerminalDNDEvent extends TsapiCallCtlTerminalEvent
		implements CallCtlTermDoNotDisturbEv {
	private boolean state = false;

	public TsapiTerminalDNDEvent(Terminal _device, boolean _state, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		state = _state;
	}

	public boolean getDoNotDisturbState() {
		return state;
	}

	public int getID() {
		return 221;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTerminalDNDEvent JD-Core
 * Version: 0.5.4
 */