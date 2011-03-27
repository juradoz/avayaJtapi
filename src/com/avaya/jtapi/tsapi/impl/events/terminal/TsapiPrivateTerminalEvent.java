package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.privatedata.events.PrivateTermEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateTerminalEvent extends TsapiTerminalEvent
		implements PrivateTermEv {
	public TsapiPrivateTerminalEvent(final Terminal _terminal,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_terminal, _cause, _metaCode, _privateData, 5);
	}

	@Override
	public int getID() {
		return 603;
	}
}
