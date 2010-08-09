package com.avaya.jtapi.tsapi.csta1;

public final class LucentQ931UserToUserInfo extends LucentUserToUserInfo {
	LucentQ931UserToUserInfo() {
		type = 8;
		data = null;
	}

	public LucentQ931UserToUserInfo(byte[] _data) {
		super(_data, (short) 8);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQ931UserToUserInfo JD-Core Version: 0.5.4
 */