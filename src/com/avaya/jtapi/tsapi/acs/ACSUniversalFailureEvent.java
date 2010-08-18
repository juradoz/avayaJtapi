package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSUniversalFailureEvent extends ACSUnsolicited {
	short error;
	public static final int PDU = 7;

	public static ACSUniversalFailureEvent decode(final InputStream in) {
		final ACSUniversalFailureEvent _this = new ACSUniversalFailureEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		error = ASNEnumerated.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 7;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSUniversalFailureEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ACSUniversalFailure.print(error, "error", indent));

		lines.add("}");
		return lines;
	}
}
