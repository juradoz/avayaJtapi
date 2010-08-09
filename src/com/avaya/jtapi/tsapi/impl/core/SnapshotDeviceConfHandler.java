package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class SnapshotDeviceConfHandler implements ConfHandler {
	TSDevice device;
	CSTASnapshotDeviceResponseInfo[] info;

	SnapshotDeviceConfHandler(TSDevice _device) {
		device = _device;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTASnapshotDeviceConfEvent))) {
			return;
		}

		info = ((CSTASnapshotDeviceConfEvent) event.getEvent())
				.getSnapshotData();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.SnapshotDeviceConfHandler JD-Core Version:
 * 0.5.4
 */