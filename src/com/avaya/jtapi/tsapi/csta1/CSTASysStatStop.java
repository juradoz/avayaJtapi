package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNNull;

public class CSTASysStatStop extends CSTARequest {
	public static final int PDU = 102;

	public static CSTASysStatStop decode(InputStream in) {
		CSTASysStatStop _this = new CSTASysStatStop();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNNull.encode(memberStream);
	}

	@Override
	public int getPDU() {
		return 102;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTASysStatStop ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}
}

