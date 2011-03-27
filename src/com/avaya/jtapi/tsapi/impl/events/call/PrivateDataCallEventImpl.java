package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.MetaEvent;
import javax.telephony.privatedata.PrivateDataEvent;

public class PrivateDataCallEventImpl implements PrivateDataEvent {
	private final MetaEvent metaEvent;
	private final Object privateData;
	private final Object source;
	private final int cause;

	public PrivateDataCallEventImpl(final Object privateData,
			final Object source, final int cause, final MetaEvent metaEvent) {
		this.privateData = privateData;
		this.source = source;
		this.cause = cause;
		this.metaEvent = metaEvent;
	}

	@Override
	public int getCause() {
		return cause;
	}

	@Override
	public int getID() {
		return 601;
	}

	@Override
	public MetaEvent getMetaEvent() {
		return metaEvent;
	}

	@Override
	public Object getPrivateData() {
		return privateData;
	}

	@Override
	public Object getSource() {
		return source;
	}
}
