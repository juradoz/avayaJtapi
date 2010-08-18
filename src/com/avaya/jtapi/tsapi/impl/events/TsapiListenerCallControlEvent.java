package com.avaya.jtapi.tsapi.impl.events;

import javax.telephony.MetaEvent;
import javax.telephony.callcontrol.CallControlEvent;

public abstract class TsapiListenerCallControlEvent extends TsapiListenerEvent
		implements CallControlEvent {
	public TsapiListenerCallControlEvent(final int eventId, final int _cause,
			final MetaEvent metaEvent, final Object source,
			final Object privateData) {
		super(eventId, _cause, metaEvent, source, privateData);
	}

	public int getCallControlCause() {
		if (cause == 101 || cause == 202 || cause == 203 || cause == 204
				|| cause == 205 || cause == 206 || cause == 207 || cause == 208
				|| cause == 209 || cause == 210 || cause == 211 || cause == 212
				|| cause == 213 || cause == 214)
			return cause;
		return 100;
	}
}
