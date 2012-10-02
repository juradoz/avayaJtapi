package com.avaya.jtapi.tsapi.impl.events;

import javax.telephony.MetaEvent;
import javax.telephony.privatedata.PrivateDataEvent;

public class PrivateDataEventImpl extends TsapiListenerEvent implements
		PrivateDataEvent {
	public PrivateDataEventImpl(int eventId, int _cause, MetaEvent metaEvent,
			Object source, Object privateData) {
		super(eventId, _cause, metaEvent, source, privateData);
	}
}