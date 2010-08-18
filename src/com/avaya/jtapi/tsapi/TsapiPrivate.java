package com.avaya.jtapi.tsapi;

public final class TsapiPrivate {
	public String vendor;
	public byte[] data;
	public int tsType;

	public TsapiPrivate(byte[] _data) {
		this(_data, false);
	}

	public TsapiPrivate(byte[] _data, boolean waitForResponse) {
		data = _data;
		if (waitForResponse) {
			tsType = 89;
		} else {
			tsType = 95;
		}
	}

	public TsapiPrivate(String _vendor, byte[] _data, int _tsType) {
		vendor = _vendor;
		data = _data;
		tsType = _tsType;
	}

	public byte[] getData() {
		return data;
	}
}

