package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.TerminalEvent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;

public class TerminalEventImpl extends TsapiListenerEvent implements
		TerminalEvent {
	private final Terminal terminal;

	public TerminalEventImpl(final int eventId, final int _cause,
			final Terminal _device, final Object privateData) {
		super(eventId, _cause, null, _device, privateData);
		terminal = _device;
	}

	@Override
	public Terminal getTerminal() {
		return terminal;
	}
}
