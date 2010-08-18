package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV6ConnectionClearedEvent extends
		LucentConnectionClearedEvent {
	static final int PDU = 115;

	static LucentConnectionClearedEvent decode(final InputStream in) {
		final LucentV6ConnectionClearedEvent _this = new LucentV6ConnectionClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	@Override
	public int getPDU() {
		return 115;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ConnectionClearedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
