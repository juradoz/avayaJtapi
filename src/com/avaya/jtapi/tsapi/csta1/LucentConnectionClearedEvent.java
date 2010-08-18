package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentConnectionClearedEvent extends LucentPrivateData {
	LucentUserToUserInfo userInfo;
	static final int PDU = 36;

	static LucentConnectionClearedEvent decode(final InputStream in) {
		final LucentConnectionClearedEvent _this = new LucentConnectionClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 36;
	}

	public LucentUserToUserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentConnectionClearedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}

	public void setUserInfo(final LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
