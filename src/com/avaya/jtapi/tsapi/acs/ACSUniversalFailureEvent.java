package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSUniversalFailureEvent extends ACSUnsolicited {
	short error;
	public static final int PDU = 7;

	public static ACSUniversalFailureEvent decode(InputStream in) {
		ACSUniversalFailureEvent _this = new ACSUniversalFailureEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.error = ACSUniversalFailure.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSUniversalFailureEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ACSUniversalFailure.print(this.error, "error", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 7;
	}
}