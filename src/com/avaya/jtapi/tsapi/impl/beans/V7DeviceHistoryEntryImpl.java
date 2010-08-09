package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.ConnectionID;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;

public final class V7DeviceHistoryEntryImpl implements V7DeviceHistoryEntry {
	private String oldDeviceID;
	private short eventCause;
	private ConnectionID oldConnectionID;

	public V7DeviceHistoryEntryImpl(String _dev, short _ec, ConnectionID _cid) {
		oldDeviceID = _dev;
		eventCause = _ec;
		oldConnectionID = _cid;
	}

	public short getEventCause() {
		return eventCause;
	}

	public ConnectionID getOldConnectionID() {
		return oldConnectionID;
	}

	public String getOldDeviceID() {
		return oldDeviceID;
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.beans.V7DeviceHistoryEntryImpl JD-Core Version:
 * 0.5.4
 */