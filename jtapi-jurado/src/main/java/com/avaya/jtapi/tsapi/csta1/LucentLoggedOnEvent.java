package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentLoggedOnEvent extends LucentPrivateData {
	short workMode;
	static final int PDU = 48;

	public static LucentLoggedOnEvent decode(InputStream in) {
		LucentLoggedOnEvent _this = new LucentLoggedOnEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.workMode = LucentWorkMode.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		LucentWorkMode.encode(this.workMode, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentLoggedOnEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 48;
	}

	public short getWorkMode() {
		return this.workMode;
	}

	public void setWorkMode(short workMode) {
		this.workMode = workMode;
	}
}