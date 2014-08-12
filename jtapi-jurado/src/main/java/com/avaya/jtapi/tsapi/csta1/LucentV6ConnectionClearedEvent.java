package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV6ConnectionClearedEvent extends
		LucentConnectionClearedEvent {
	static final int PDU = 115;

	static LucentConnectionClearedEvent decode(InputStream in) {
		LucentV6ConnectionClearedEvent _this = new LucentV6ConnectionClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ConnectionClearedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 115;
	}
}