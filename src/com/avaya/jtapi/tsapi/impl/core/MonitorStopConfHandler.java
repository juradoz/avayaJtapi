package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class MonitorStopConfHandler implements ConfHandler {
	TSDevice device;

	MonitorStopConfHandler(TSDevice _device) {
		device = _device;
	}

	public void handleConf(CSTAEvent event) {
		if (device.monitorDeviceXRefID != 0) {
			device.getTSProviderImpl()
					.deleteMonitor(device.monitorDeviceXRefID);
			device.monitorDeviceXRefID = 0;
		}
		device.testDelete();
	}
}

