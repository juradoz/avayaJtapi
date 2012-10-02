package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.privatedata.events.PrivateTermEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateTerminalEvent extends TsapiTerminalEvent
		implements PrivateTermEv {
	public int getID() {
		return 603;
	}

	public TsapiPrivateTerminalEvent(Terminal _terminal, int _cause,
			int _metaCode, Object _privateData) {
		super(_terminal, _cause, _metaCode, _privateData, 5);
	}
}