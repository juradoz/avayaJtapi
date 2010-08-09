package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.TerminalEvent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;

public class TerminalEventImpl extends TsapiListenerEvent implements
		TerminalEvent {
	private Terminal terminal;

	public TerminalEventImpl(int eventId, int _cause, Terminal _device,
			Object privateData) {
		super(eventId, _cause, null, _device, privateData);
		terminal = _device;
	}

	public Terminal getTerminal() {
		return terminal;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventImpl JD-Core Version:
 * 0.5.4
 */