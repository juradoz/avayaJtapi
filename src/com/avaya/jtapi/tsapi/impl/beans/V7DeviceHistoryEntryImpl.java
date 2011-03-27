package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.ConnectionID;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;

public final class V7DeviceHistoryEntryImpl implements V7DeviceHistoryEntry {
	private String oldDeviceID;
	private short eventCause;
	private ConnectionID oldConnectionID;

	public V7DeviceHistoryEntryImpl(final String _dev, final short _ec,
			final ConnectionID _cid) {
		oldDeviceID = _dev;
		eventCause = _ec;
		oldConnectionID = _cid;
	}

	@Override
	public short getEventCause() {
		return eventCause;
	}

	@Override
	public ConnectionID getOldConnectionID() {
		return oldConnectionID;
	}

	@Override
	public String getOldDeviceID() {
		return oldDeviceID;
	}

	public void setEventCause(final short eventCause) {
		this.eventCause = eventCause;
	}

	public void setOldConnectionID(final ConnectionID oldConnectionID) {
		this.oldConnectionID = oldConnectionID;
	}

	public void setOldDeviceID(final String oldDeviceID) {
		this.oldDeviceID = oldDeviceID;
	}
}
