package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class LucentPResponse extends LucentPrivateData {
	byte[] value;
	static final int PDU = 122;

	LucentPResponse(byte[] _val) {
		this.value = _val;
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNOctetString.encode(this.value, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentPResponse ::=");
		lines.add("{");

		String indent = "   ";
		lines.add(new StringBuilder()
				.append(indent)
				.append("value ")
				.append(this.value != null ? Arrays
						.asList(new byte[][] { this.value }) : "<null>")
				.toString());

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 122;
	}
}