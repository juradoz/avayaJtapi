package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.MetaEvent;
import javax.telephony.Terminal;

public class TerminalEventParams {
	int eventId;
	int cause;
	MetaEvent metaEvent;
	Object source;
	Terminal terminal;
	Object privateData;

	public int getCause() {
		return cause;
	}

	public int getEventId() {
		return eventId;
	}

	public MetaEvent getMetaEvent() {
		return metaEvent;
	}

	public Object getPrivateData() {
		return privateData;
	}

	public Object getSource() {
		return source;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setCause(int cause) {
		this.cause = cause;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public void setMetaEvent(MetaEvent metaEvent) {
		this.metaEvent = metaEvent;
	}

	public void setPrivateData(Object o) {
		privateData = o;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventParams JD-Core
 * Version: 0.5.4
 */