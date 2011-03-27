package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcontrol.events.CallCtlTermDoNotDisturbEv;

@SuppressWarnings("deprecation")
public final class TsapiTerminalDNDEvent extends TsapiCallCtlTerminalEvent
		implements CallCtlTermDoNotDisturbEv {
	private boolean state = false;

	public TsapiTerminalDNDEvent(final Terminal _device, final boolean _state,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		state = _state;
	}

	@Override
	public boolean getDoNotDisturbState() {
		return state;
	}

	@Override
	public int getID() {
		return 221;
	}
}
