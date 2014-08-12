package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import java.util.Vector;

final class DeviceObs {
	TsapiCallMonitor callback;
	Vector<DevWithType> devWithTypeVector = new Vector<DevWithType>();

	DeviceObs(TsapiCallMonitor _callback) {
		this.callback = _callback;
	}
}