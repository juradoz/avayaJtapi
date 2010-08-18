package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateDeleted extends TSDeviceState {
	@Override
	void recreate(final TSDevice _tsDevice) {
		_tsDevice.internalRecreate();
	}

	@Override
	public String toString() {
		return "Deleted";
	}

	@Override
	boolean wasDeleteDone() {
		return true;
	}
}
