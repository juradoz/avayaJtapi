package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class CSTAQueryLastNumber extends CSTARequest {
	String device;
	static final int PDU = 35;

	CSTAQueryLastNumber(String _device) {
		this.device = _device;
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.device, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryLastNumber ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 35;
	}

	public String getDevice() {
		return this.device;
	}
}