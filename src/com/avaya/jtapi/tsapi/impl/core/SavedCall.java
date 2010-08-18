package com.avaya.jtapi.tsapi.impl.core;

final class SavedCall {
	TSCall call;
	long saveTime;

	SavedCall(final TSCall _call) {
		call = _call;
		saveTime = System.currentTimeMillis();
	}
}
