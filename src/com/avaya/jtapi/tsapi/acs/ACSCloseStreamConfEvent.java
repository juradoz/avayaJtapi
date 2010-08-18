package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNNull;

public final class ACSCloseStreamConfEvent extends ACSConfirmation {
	public static final int PDU = 4;

	public static ACSCloseStreamConfEvent decode(InputStream in) {
		ACSCloseStreamConfEvent _this = new ACSCloseStreamConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 4;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSCloseStreamConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}
}

