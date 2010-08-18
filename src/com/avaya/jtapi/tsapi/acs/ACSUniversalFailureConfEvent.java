package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSUniversalFailureConfEvent extends ACSConfirmation {
	public static ACSUniversalFailureConfEvent decode(final InputStream in) {
		final ACSUniversalFailureConfEvent _this = new ACSUniversalFailureConfEvent();
		_this.doDecode(in);

		return _this;
	}

	short error;

	public static final int PDU = 6;

	public ACSUniversalFailureConfEvent() {
	}

	public ACSUniversalFailureConfEvent(final short _error) {
		error = _error;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		error = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(error, memberStream);
	}

	public short getError() {
		return error;
	}

	@Override
	public int getPDU() {
		return 6;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSUniversalFailureConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ACSUniversalFailure.print(error, "error", indent));

		lines.add("}");
		return lines;
	}
}
