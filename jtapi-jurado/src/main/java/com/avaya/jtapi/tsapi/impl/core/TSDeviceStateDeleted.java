package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateDeleted extends TSDeviceState {
	void recreate(TSDevice _tsDevice) {
		_tsDevice.internalRecreate();
	}

	public String toString() {
		return "Deleted";
	}

	boolean wasDeleteDone() {
		return true;
	}
}