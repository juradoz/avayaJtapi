package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class SnapshotDeviceConfHandler implements ConfHandler {
	TSDevice device;
	CSTASnapshotDeviceResponseInfo[] info;

	SnapshotDeviceConfHandler(TSDevice _device) {
		this.device = _device;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTASnapshotDeviceConfEvent))) {
			return;
		}

		this.info = ((CSTASnapshotDeviceConfEvent) event.getEvent())
				.getSnapshotData();
	}
}