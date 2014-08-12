package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentQueryTg extends LucentPrivateData {
	String device;
	static final int PDU = 26;

	public LucentQueryTg() {
	}

	public LucentQueryTg(String _device) {
		this.device = _device;
	}

	public static LucentQueryTg decode(InputStream in) {
		LucentQueryTg _this = new LucentQueryTg();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.device, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryTg ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 26;
	}
}