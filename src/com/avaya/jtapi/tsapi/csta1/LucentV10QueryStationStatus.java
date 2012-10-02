package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV10QueryStationStatus extends LucentQueryStationStatus {
	static final int PDU = 150;

	public LucentV10QueryStationStatus() {
	}

	public LucentV10QueryStationStatus(String _device) {
		super(_device);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	public static LucentV10QueryStationStatus decode(InputStream in) {
		LucentV10QueryStationStatus _this = new LucentV10QueryStationStatus();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV10QueryStationStatus ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 150;
	}

	public String getDeviceID() {
		return this.device;
	}
}