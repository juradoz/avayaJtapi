package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateDeleted extends TSDeviceState {
	@Override
	void recreate(TSDevice _tsDevice) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSDeviceStateDeleted JD-Core Version: 0.5.4
 */