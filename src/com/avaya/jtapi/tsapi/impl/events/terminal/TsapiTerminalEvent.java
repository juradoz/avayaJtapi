package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.privatedata.events.PrivateTermEv;

import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;

@SuppressWarnings("deprecation")
public abstract class TsapiTerminalEvent extends TsapiObserverEvent implements
		PrivateTermEv {
	private Terminal terminal = null;

	public TsapiTerminalEvent(final Terminal _terminal, final int _cause,
			final int _metaCode, final Object _privateData) {
		this(_terminal, _cause, _metaCode, _privateData, 0);
	}

	public TsapiTerminalEvent(final Terminal _terminal, final int _cause,
			final int _metaCode, final Object _privateData,
			final int _eventPackage) {
		super(_cause, _metaCode, _privateData, _eventPackage);
		terminal = _terminal;
	}

	@Override
	public final Terminal getTerminal() {
		return terminal;
	}
}
