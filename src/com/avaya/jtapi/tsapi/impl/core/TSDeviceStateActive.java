package com.avaya.jtapi.tsapi.impl.core;

class TSDeviceStateActive extends TSDeviceState {
	@Override
	void recreate(TSDevice _tsDevice) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSDeviceStateActive JD-Core Version: 0.5.4
 */