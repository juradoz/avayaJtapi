package com.avaya.jtapi.tsapi;

public final class Q931UserToUserInfo extends UserToUserInfo {
	Q931UserToUserInfo() {
		super(null, (short) 8);
	}

	public Q931UserToUserInfo(byte[] _data) {
		super(_data, (short) 8);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.Q931UserToUserInfo JD-Core Version: 0.5.4
 */