package com.avaya.jtapi.tsapi;

public final class Q931UserToUserInfo extends UserToUserInfo {
	public Q931UserToUserInfo(byte[] _data) {
		super(_data, (short) 8);
	}

	Q931UserToUserInfo() {
		super(null, (short) 8);
	}
}