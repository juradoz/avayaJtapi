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

	CSTAEvent(ACSEventHeader _eventHeader, TsapiPDU _event) {
		eventHeader = _eventHeader;
		event = _event;
		privData = null;
	}

	public CSTAEvent(ACSEventHeader _eventHeader, TsapiPDU _event,
			CSTAPrivate _priv) {
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
		if (event == null) {
			s = "-";
		} else {
			s = event.getClass().getName();
			int i = s.lastIndexOf('.');

			s = (i >= 0) ? s.substring(i + 1) : s;
		}
		return s;
	}

	public Object getPrivData() {
		return privData;
	}

	public long getQueuedTimeStamp() {
		return queuedTimeStamp;
	}

	public void setPrivData(Object privData) {
		this.privData = privData;
	}

	public void setQueuedTimeStamp(long queuedTimeStamp) {
		this.queuedTimeStamp = queuedTimeStamp;
	}

	@Override
	public String toString() {
		return "CSTAEvent[" + getMyCustomString() + "]@"
				+ Integer.toHexString(super.hashCode());
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAEvent JD-Core Version: 0.5.4
 */