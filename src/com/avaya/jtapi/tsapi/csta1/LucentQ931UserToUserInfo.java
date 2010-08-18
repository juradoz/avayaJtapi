package com.avaya.jtapi.tsapi.csta1;

public final class LucentQ931UserToUserInfo extends LucentUserToUserInfo {
	LucentQ931UserToUserInfo() {
		type = 8;
		data = null;
	}

	public LucentQ931UserToUserInfo(final byte[] _data) {
		super(_data, (short) 8);
	}
}
