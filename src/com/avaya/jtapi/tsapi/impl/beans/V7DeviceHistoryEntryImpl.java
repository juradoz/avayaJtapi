package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.ConnectionID;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;

public final class V7DeviceHistoryEntryImpl implements V7DeviceHistoryEntry {
	private String oldDeviceID;
	private short eventCause;
	private ConnectionID oldConnectionID;

	public V7DeviceHistoryEntryImpl(String _dev, short _ec, ConnectionID _cid) {
		this.oldDeviceID = _dev;
		this.eventCause = _ec;
		this.oldConnectionID = _cid;
	}

	public String getOldDeviceID() {
		return this.oldDeviceID;
	}

	public short getEventCause() {
		return this.eventCause;
	}

	public ConnectionID getOldConnectionID() {
		return this.oldConnectionID;
	}

	public void setEventCause(short eventCause) {
		this.eventCause = eventCause;
	}

	public void setOldConnectionID(ConnectionID oldConnectionID) {
		this.oldConnectionID = oldConnectionID;
	}

	public void setOldDeviceID(String oldDeviceID) {
		this.oldDeviceID = oldDeviceID;
	}
}