package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.MetaEvent;
import javax.telephony.callcontrol.CallControlForwarding;

public class AddressEventParams {
	CallControlForwarding[] callControlForwarding;
	boolean doNotDisturbState;
	int mwBits;
	int eventId;
	MetaEvent metaEvent;
	int cause;
	Object source;
	private Object privateData;

	public AddressEventParams() {
		mwBits = 0;
	}

	public CallControlForwarding[] getCallControlForwarding() {
		return callControlForwarding;
	}

	public int getCause() {
		return cause;
	}

	public int getEventId() {
		return eventId;
	}

	public MetaEvent getMetaEvent() {
		return metaEvent;
	}

	public int getMwBits() {
		return mwBits;
	}

	public Object getPrivateData() {
		return privateData;
	}

	public Object getSource() {
		return source;
	}

	public boolean isDoNotDisturbState() {
		return doNotDisturbState;
	}

	public void setCallControlForwarding(
			CallControlForwarding[] callControlForwarding) {
		this.callControlForwarding = callControlForwarding;
	}

	public void setCause(int cause) {
		this.cause = cause;
	}

	public void setDoNotDisturbState(boolean doNotDisturbState) {
		this.doNotDisturbState = doNotDisturbState;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public void setMetaEvent(MetaEvent metaEvent) {
		this.metaEvent = metaEvent;
	}

	public void setMwBits(int mwBits) {
		this.mwBits = mwBits;
	}

	public void setPrivateData(Object privateData) {
		this.privateData = privateData;
	}

	public void setSource(Object source) {
		this.source = source;
	}
}
