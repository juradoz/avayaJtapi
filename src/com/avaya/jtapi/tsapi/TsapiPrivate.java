package com.avaya.jtapi.tsapi;

public final class TsapiPrivate {
	public String vendor;
	public byte[] data;
	public int tsType;

	public TsapiPrivate(byte[] _data) {
		this(_data, false);
	}

	public TsapiPrivate(byte[] _data, boolean waitForResponse) {
		this.data = _data;
		if (waitForResponse) {
			this.tsType = 89;
		} else {
			this.tsType = 95;
		}
	}

	public TsapiPrivate(String _vendor, byte[] _data, int _tsType) {
		this.vendor = _vendor;
		this.data = _data;
		this.tsType = _tsType;
	}

	public byte[] getData() {
		return this.data;
	}
}