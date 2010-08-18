package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;

final class CallbackAndType {
	TsapiCallMonitor callback;
	DevWithType devWithType;

	CallbackAndType(final TsapiCallMonitor _callback,
			final DevWithType _devWithType) {
		callback = _callback;
		devWithType = _devWithType;
	}
}
