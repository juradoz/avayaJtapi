package com.avaya.jtapi.tsapi;

public abstract interface V7DeviceHistoryEntry {
	public abstract short getEventCause();

	public abstract ConnectionID getOldConnectionID();

	public abstract String getOldDeviceID();
}

