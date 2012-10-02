package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.MetaEvent;
import javax.telephony.callcontrol.CallControlForwarding;

public class AddressEventParams {
	CallControlForwarding[] callControlForwarding;
	boolean doNotDisturbState;
	int mwBits = 0;
	int eventId;
	MetaEvent metaEvent;
	int cause;
	Object source;
	private Object privateData;

	public Object getSource() {
		return this.source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public void setMetaEvent(MetaEvent metaEvent) {
		this.metaEvent = metaEvent;
	}

	public int getEventId() {
		return this.eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getCause() {
		return this.cause;
	}

	public void setCause(int cause) {
		this.cause = cause;
	}

	public MetaEvent getMetaEvent() {
		return this.metaEvent;
	}

	public CallControlForwarding[] getCallControlForwarding() {
		return this.callControlForwarding;
	}

	public void setCallControlForwarding(
			CallControlForwarding[] callControlForwarding) {
		this.callControlForwarding = callControlForwarding;
	}

	public boolean isDoNotDisturbState() {
		return this.doNotDisturbState;
	}

	public void setDoNotDisturbState(boolean doNotDisturbState) {
		this.doNotDisturbState = doNotDisturbState;
	}

	public int getMwBits() {
		return this.mwBits;
	}

	public void setMwBits(int mwBits) {
		this.mwBits = mwBits;
	}

	public Object getPrivateData() {
		return this.privateData;
	}

	public void setPrivateData(Object privateData) {
		this.privateData = privateData;
	}
}