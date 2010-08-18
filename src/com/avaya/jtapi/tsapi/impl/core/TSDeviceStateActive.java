package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateActive extends TSDeviceState {
	@Override
	void recreate(final TSDevice _tsDevice) {
	}

	@Override
	public String toString() {
		return "Active";
	}

	@Override
	boolean wasDeleteDone() {
		return false;
	}
}
