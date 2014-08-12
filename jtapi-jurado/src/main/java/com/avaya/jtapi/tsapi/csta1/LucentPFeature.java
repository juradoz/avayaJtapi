package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

class LucentPFeature extends LucentPrivateData {
	byte[] value = new byte[32];
	static final int PDU = 120;

	public void encodeMembers(OutputStream memberStream) {
		Random r = new Random();
		r.nextBytes(this.value);
		ASNOctetString.encode(this.value, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentPFeature ::=");
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
		return 120;
	}
}