package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTAUniversalFailureConfEvent extends CSTAConfirmation {
	public static CSTAUniversalFailureConfEvent decode(InputStream in) {
		CSTAUniversalFailureConfEvent _this = new CSTAUniversalFailureConfEvent();
		_this.doDecode(in);

		return _this;
	}

	short error;

	public static final int PDU = 53;

	public CSTAUniversalFailureConfEvent() {
	}

	public CSTAUniversalFailureConfEvent(short _error) {
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
		return 53;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("CSTAUniversalFailureConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAUniversalFailure.print(error, "error", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAUniversalFailureConfEvent JD-Core Version:
 * 0.5.4
 */