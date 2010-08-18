package com.avaya.jtapi.tsapi;

public final class V7NetworkProgressInfo extends V5NetworkProgressInfo {
	V7DeviceHistoryEntry[] deviceHistory;

	public V7NetworkProgressInfo() {
	}

	V7NetworkProgressInfo(final V7DeviceHistoryEntry[] _deviceHistory,
			final String _trunkGroup, final String _trunkMember,
			final TsapiTrunk _trunk, final short progressLocation,
			final short progressDescription) {
		super(_trunkGroup, _trunkMember, _trunk, progressLocation,
				progressDescription);
		deviceHistory = _deviceHistory;
	}

	V7DeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	public void setDeviceHistory(final V7DeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}
