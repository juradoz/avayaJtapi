package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class LucentPChallenge extends LucentPrivateData {
	byte[] value;
	static final int PDU = 121;

	static LucentPChallenge decode(InputStream in) {
		LucentPChallenge _this = new LucentPChallenge();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.value = ASNOctetString.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentPChallenge ::=");
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
		return 121;
	}
}