package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateBeingDeleted extends TSDeviceState {
	void recreate(TSDevice _tsDevice) {
	}

	public String toString() {
		return "BeingDeleted";
	}

	boolean wasDeleteDone() {
		return false;
	}
}