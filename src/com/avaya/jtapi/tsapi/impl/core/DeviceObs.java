package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;

final class DeviceObs {
	TsapiCallMonitor callback;
	Vector<DevWithType> devWithTypeVector = new Vector<DevWithType>();

	DeviceObs(final TsapiCallMonitor _callback) {
		callback = _callback;
	}
}
