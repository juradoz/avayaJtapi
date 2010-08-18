package com.avaya.jtapi.tsapi.impl.events;

import javax.telephony.MetaEvent;
import javax.telephony.callcenter.CallCenterEvent;

public abstract class TsapiListenerCallCenterEvent extends TsapiListenerEvent
		implements CallCenterEvent {
	public TsapiListenerCallCenterEvent(final int eventId, final int _cause,
			final MetaEvent metaEvent, final Object source,
			final Object privateData) {
		super(eventId, _cause, metaEvent, source, privateData);
	}

	public int getCallCenterCause() {
		if (cause == 101 || cause == 302)
			return cause;
		return 100;
	}
}
