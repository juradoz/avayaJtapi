package com.avaya.jtapi.tsapi.impl.core;

final class SavedCall {
	TSCall call;
	long saveTime;

	SavedCall(TSCall _call) {
		this.call = _call;
		this.saveTime = System.currentTimeMillis();
	}
}