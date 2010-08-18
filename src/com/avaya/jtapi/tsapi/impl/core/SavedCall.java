package com.avaya.jtapi.tsapi.impl.core;

final class SavedCall {
	TSCall call;
	long saveTime;

	SavedCall(TSCall _call) {
		call = _call;
		saveTime = System.currentTimeMillis();
	}
}

