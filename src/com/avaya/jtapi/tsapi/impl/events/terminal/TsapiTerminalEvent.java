package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.privatedata.events.PrivateTermEv;

import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;

@SuppressWarnings("deprecation")
public abstract class TsapiTerminalEvent extends TsapiObserverEvent implements
		PrivateTermEv {
	private Terminal terminal = null;

	public TsapiTerminalEvent(Terminal _terminal, int _cause, int _metaCode,
			Object _privateData) {
		this(_terminal, _cause, _metaCode, _privateData, 0);
	}

	public TsapiTerminalEvent(Terminal _terminal, int _cause, int _metaCode,
			Object _privateData, int _eventPackage) {
		super(_cause, _metaCode, _privateData, _eventPackage);
		terminal = _terminal;
	}

	public final Terminal getTerminal() {
		return terminal;
	}
}

