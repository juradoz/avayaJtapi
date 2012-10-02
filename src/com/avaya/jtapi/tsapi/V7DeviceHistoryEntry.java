package com.avaya.jtapi.tsapi;

public abstract interface V7DeviceHistoryEntry {
	public abstract String getOldDeviceID();

	public abstract short getEventCause();

	public abstract ConnectionID getOldConnectionID();
}