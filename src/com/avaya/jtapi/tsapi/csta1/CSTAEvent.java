package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public final class CSTAEvent {
	ACSEventHeader eventHeader;
	TsapiPDU event;
	Object privData;
	long queuedTimeStamp;

	CSTAEvent() {
		this.eventHeader = null;
		this.event = null;
		this.privData = null;
	}

	CSTAEvent(ACSEventHeader _eventHeader, TsapiPDU _event) {
		this.eventHeader = _eventHeader;
		this.event = _event;
		this.privData = null;
	}

	public CSTAEvent(ACSEventHeader _eventHeader, TsapiPDU _event,
			CSTAPrivate _priv) {
		this.eventHeader = _eventHeader;
		this.event = _event;
		this.privData = _priv;
	}

	public TsapiPDU getEvent() {
		return this.event;
	}

	public ACSEventHeader getEventHeader() {
		return this.eventHeader;
	}

	public Object getPrivData() {
		return this.privData;
	}

	public void setPrivData(Object privData) {
		this.privData = privData;
	}

	public String toString() {
		return "CSTAEvent[" + getMyCustomString() + "]@"
				+ Integer.toHexString(hashCode());
	}

	private String getMyCustomString() {
		String s;
		if (this.event == null) {
			s = "-";
		} else {
			s = this.event.getClass().getName();
			int i = s.lastIndexOf('.');

			s = i >= 0 ? s.substring(i + 1) : s;
		}
		return s;
	}

	public long getQueuedTimeStamp() {
		return this.queuedTimeStamp;
	}

	public void setQueuedTimeStamp(long queuedTimeStamp) {
		this.queuedTimeStamp = queuedTimeStamp;
	}
}