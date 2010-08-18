package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTAUniversalFailureConfEvent extends CSTAConfirmation {
	public static CSTAUniversalFailureConfEvent decode(final InputStream in) {
		final CSTAUniversalFailureConfEvent _this = new CSTAUniversalFailureConfEvent();
		_this.doDecode(in);

		return _this;
	}

	short error;

	public static final int PDU = 53;

	public CSTAUniversalFailureConfEvent() {
	}

	public CSTAUniversalFailureConfEvent(final short _error) {
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
		return 53;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAUniversalFailureConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAUniversalFailure.print(error, "error", indent));

		lines.add("}");
		return lines;
	}
}
