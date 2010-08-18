package com.avaya.jtapi.tsapi.impl.events;

import javax.telephony.MetaEvent;
import javax.telephony.privatedata.PrivateDataEvent;

public class PrivateDataEventImpl extends TsapiListenerEvent implements
		PrivateDataEvent {
	public PrivateDataEventImpl(final int eventId, final int _cause,
			final MetaEvent metaEvent, final Object source,
			final Object privateData) {
		super(eventId, _cause, metaEvent, source, privateData);
	}
}
