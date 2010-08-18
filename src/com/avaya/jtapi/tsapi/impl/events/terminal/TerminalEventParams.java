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

	public void setCause(final int cause) {
		this.cause = cause;
	}

	public void setEventId(final int eventId) {
		this.eventId = eventId;
	}

	public void setMetaEvent(final MetaEvent metaEvent) {
		this.metaEvent = metaEvent;
	}

	public void setPrivateData(final Object o) {
		privateData = o;
	}

	public void setSource(final Object source) {
		this.source = source;
	}

	public void setTerminal(final Terminal terminal) {
		this.terminal = terminal;
	}
}
