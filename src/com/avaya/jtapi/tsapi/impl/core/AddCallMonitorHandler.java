package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

final class AddCallMonitorHandler implements SnapshotCallExtraConfHandler {
	TSCall call;
	int monitorCrossRefID;

	AddCallMonitorHandler(final TSCall _call, final int _monitorCrossRefID) {
		call = _call;
		monitorCrossRefID = _monitorCrossRefID;
	}

	public Object handleConf(final boolean rc, final Vector<TSEvent> eventList,
			final Object privateData) {
		call.monitorCrossRefID = monitorCrossRefID;
		call.getTSProviderImpl().addMonitor(monitorCrossRefID, call);

		if (call.internalDeviceMonitor != null) {
			call.internalDeviceMonitor.removeInternalMonitor(call);
			call.internalDeviceMonitor = null;
		}
		return null;
	}
}
