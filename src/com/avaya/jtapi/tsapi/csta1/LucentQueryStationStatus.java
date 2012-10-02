package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentQueryStationStatus extends LucentPrivateData {
	String device;
	static final int PDU = 22;

	public LucentQueryStationStatus() {
	}

	public LucentQueryStationStatus(String _device) {
		this.device = _device;
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.device, memberStream);
	}

	public static LucentQueryStationStatus decode(InputStream in) {
		LucentQueryStationStatus _this = new LucentQueryStationStatus();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.device = DeviceID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryStationStatus ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 22;
	}
}