package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateActive extends TSDeviceState {
	void recreate(TSDevice _tsDevice) {
	}

	public String toString() {
		return "Active";
	}

	boolean wasDeleteDone() {
		return false;
	}
}