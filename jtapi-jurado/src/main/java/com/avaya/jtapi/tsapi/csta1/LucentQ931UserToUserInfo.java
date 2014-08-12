package com.avaya.jtapi.tsapi.csta1;

public final class LucentQ931UserToUserInfo extends LucentUserToUserInfo {
	public LucentQ931UserToUserInfo(byte[] _data) {
		super(_data, (short) 8);
	}

	LucentQ931UserToUserInfo() {
		this.type = 8;
		this.data = null;
	}
}