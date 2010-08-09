package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSUniversalFailureConfEvent extends ACSConfirmation {
	public static ACSUniversalFailureConfEvent decode(InputStream in) {
		ACSUniversalFailureConfEvent _this = new ACSUniversalFailureConfEvent();
		_this.doDecode(in);

		return _this;
	}

	short error;

	public static final int PDU = 6;

	public ACSUniversalFailureConfEvent() {
	}

	public ACSUniversalFailureConfEvent(short _error) {
		error = _error;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		error = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("ACSUniversalFailureConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ACSUniversalFailure.print(error, "error", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSUniversalFailureConfEvent JD-Core Version: 0.5.4
 */