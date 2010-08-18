package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateBeingDeleted extends TSDeviceState {
	@Override
	void recreate(TSDevice _tsDevice) {
	}

	@Override
	public String toString() {
		return "BeingDeleted";
	}

	@Override
	boolean wasDeleteDone() {
		return false;
	}
}
