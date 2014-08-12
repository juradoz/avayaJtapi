package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNNull;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentQueryTod extends LucentPrivateData {
	static final int PDU = 24;

	public void encodeMembers(OutputStream memberStream) {
		ASNNull.encode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryTod ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 24;
	}
}