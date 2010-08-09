package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;

final class DeviceObs {
	TsapiCallMonitor callback;
	Vector<DevWithType> devWithTypeVector = new Vector();

	DeviceObs(TsapiCallMonitor _callback) {
		callback = _callback;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.DeviceObs JD-Core Version: 0.5.4
 */