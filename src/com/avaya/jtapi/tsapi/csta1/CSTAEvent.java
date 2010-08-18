package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public final class CSTAEvent {
	ACSEventHeader eventHeader;
	TsapiPDU event;
	Object privData;
	long queuedTimeStamp;

	CSTAEvent() {
		eventHeader = null;
		event = null;
		privData = null;
	}

	CSTAEvent(final ACSEventHeader _eventHeader, final TsapiPDU _event) {
		eventHeader = _eventHeader;
		event = _event;
		privData = null;
	}

	public CSTAEvent(final ACSEventHeader _eventHeader, final TsapiPDU _event,
			final CSTAPrivate _priv) {
		eventHeader = _eventHeader;
		event = _event;
		privData = _priv;
	}

	public TsapiPDU getEvent() {
		return event;
	}

	public ACSEventHeader getEventHeader() {
		return eventHeader;
	}

	private String getMyCustomString() {
		String s;
		if (event == null)
			s = "-";
		else {
			s = event.getClass().getName();
			final int i = s.lastIndexOf('.');

			s = i >= 0 ? s.substring(i + 1) : s;
		}
		return s;
	}

	public Object getPrivData() {
		return privData;
	}

	public long getQueuedTimeStamp() {
		return queuedTimeStamp;
	}

	public void setPrivData(final Object privData) {
		this.privData = privData;
	}

	public void setQueuedTimeStamp(final long queuedTimeStamp) {
		this.queuedTimeStamp = queuedTimeStamp;
	}

	@Override
	public String toString() {
		return "CSTAEvent[" + getMyCustomString() + "]@"
				+ Integer.toHexString(super.hashCode());
	}
}
