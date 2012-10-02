package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class MonitorStopConfHandler implements ConfHandler {
	TSDevice device;

	MonitorStopConfHandler(TSDevice _device) {
		this.device = _device;
	}

	public void handleConf(CSTAEvent event) {
		if (this.device.monitorDeviceXRefID != 0) {
			this.device.getTSProviderImpl().deleteMonitor(
					this.device.monitorDeviceXRefID);
			this.device.monitorDeviceXRefID = 0;
		}
		this.device.testDelete();
	}
}