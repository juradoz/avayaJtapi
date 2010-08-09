package com.avaya.jtapi.tsapi.impl.events;

import javax.telephony.MetaEvent;
import javax.telephony.privatedata.PrivateDataEvent;

public abstract class TsapiListenerEvent implements PrivateDataEvent {
	protected final int cause;
	private final int eventId;
	private final Object source;
	private final MetaEvent metaEvent;
	private final Object privateData;

	public TsapiListenerEvent(int eventId, int _cause, MetaEvent metaEvent,
			Object source, Object privateData) {
		cause = _cause;
		this.metaEvent = metaEvent;
		this.eventId = eventId;
		this.source = source;
		this.privateData = privateData;
	}

	public final int getCause() {
		if ((cause == 101) || (cause == 102) || (cause == 103)
				|| (cause == 104) || (cause == 105) || (cause == 106)
				|| (cause == 107) || (cause == 108) || (cause == 109)
				|| (cause == 110)) {
			return cause;
		}
		return 100;
	}

	public int getID() {
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

	@Override
	public String toString() {
		return "cause=" + cause + ";eventId=" + eventId + ";source=" + source
				+ ";metaEvent=" + metaEvent;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent JD-Core Version: 0.5.4
 */