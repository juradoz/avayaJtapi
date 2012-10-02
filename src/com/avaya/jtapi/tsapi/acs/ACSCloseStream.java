package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNNull;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSCloseStream extends ACSRequest {
	public static final int PDU = 3;

	public void encodeMembers(OutputStream memberStream) {
		ASNNull.encode(memberStream);
	}

	public static ACSCloseStream decode(InputStream in) {
		ACSCloseStream _this = new ACSCloseStream();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSCloseStream ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 3;
	}
}