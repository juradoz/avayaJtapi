package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

final class AddCallMonitorHandler implements SnapshotCallExtraConfHandler {
	TSCall call;
	int monitorCrossRefID;

	AddCallMonitorHandler(TSCall _call, int _monitorCrossRefID) {
		this.call = _call;
		this.monitorCrossRefID = _monitorCrossRefID;
	}

	public Object handleConf(boolean rc, Vector<TSEvent> eventList,
			Object privateData) {
		this.call.monitorCrossRefID = this.monitorCrossRefID;
		this.call.getTSProviderImpl().addMonitor(this.monitorCrossRefID,
				this.call);

		if (this.call.internalDeviceMonitor != null) {
			this.call.internalDeviceMonitor.removeInternalMonitor(this.call);
			this.call.internalDeviceMonitor = null;
		}
		return null;
	}
}