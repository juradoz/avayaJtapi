package com.avaya.jtapi.tsapi;

public abstract interface V7OriginalCallInfo extends V5OriginalCallInfo {
	public abstract V7DeviceHistoryEntry[] getDeviceHistory();

	public abstract boolean hasDeviceHistory();
}