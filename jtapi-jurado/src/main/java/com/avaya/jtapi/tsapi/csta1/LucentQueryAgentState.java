package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentQueryAgentState extends LucentPrivateData {
	String split;
	public static final int PDU = 16;

	public LucentQueryAgentState() {
	}

	public LucentQueryAgentState(String _split) {
		this.split = _split;
	}

	public static LucentQueryAgentState decode(InputStream in) {
		LucentQueryAgentState _this = new LucentQueryAgentState();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.split = DeviceID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.split, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.split, "split", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 16;
	}
}