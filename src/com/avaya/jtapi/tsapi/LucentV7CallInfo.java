package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.CallCenterAddress;

public abstract interface LucentV7CallInfo extends LucentV5CallInfo {
	public abstract V7DeviceHistoryEntry[] getDeviceHistory();

	public abstract CallCenterAddress getDistributingVDNAddress();
}

