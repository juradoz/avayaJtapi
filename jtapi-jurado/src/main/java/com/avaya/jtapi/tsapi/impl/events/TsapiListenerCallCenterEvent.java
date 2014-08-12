package com.avaya.jtapi.tsapi.impl.events;

import javax.telephony.MetaEvent;
import javax.telephony.callcenter.CallCenterEvent;

public abstract class TsapiListenerCallCenterEvent extends TsapiListenerEvent
		implements CallCenterEvent {
	public TsapiListenerCallCenterEvent(int eventId, int _cause,
			MetaEvent metaEvent, Object source, Object privateData) {
		super(eventId, _cause, metaEvent, source, privateData);
	}

	public int getCallCenterCause() {
		if ((this.cause == 101) || (this.cause == 302)) {
			return this.cause;
		}
		return 100;
	}
}