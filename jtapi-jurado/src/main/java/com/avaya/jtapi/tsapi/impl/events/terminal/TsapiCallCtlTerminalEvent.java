package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;

public abstract class TsapiCallCtlTerminalEvent extends TsapiTerminalEvent {
	public TsapiCallCtlTerminalEvent(Terminal _device, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _cause, _metaCode, _privateData, 1);
	}
}