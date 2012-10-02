package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.V7OriginalCallInfo;

public final class V7OriginalCallInfoImpl extends V5OriginalCallInfoImpl
		implements V7OriginalCallInfo {
	private V7DeviceHistoryEntry[] deviceHistory;

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		return this.deviceHistory;
	}

	public boolean hasDeviceHistory() {
		return this.deviceHistory != null;
	}

	public void setDeviceHistory(V7DeviceHistoryEntry[] _deviceHistory) {
		this.deviceHistory = _deviceHistory;
	}
}