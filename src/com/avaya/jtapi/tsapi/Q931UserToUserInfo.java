package com.avaya.jtapi.tsapi;

public final class Q931UserToUserInfo extends UserToUserInfo {
	Q931UserToUserInfo() {
		super(null, (short) 8);
	}

	public Q931UserToUserInfo(final byte[] _data) {
		super(_data, (short) 8);
	}
}
