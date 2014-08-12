package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAUniversalFailureConfEvent extends CSTAConfirmation {
	short error;
	public static final int PDU = 53;

	public CSTAUniversalFailureConfEvent() {
	}

	public CSTAUniversalFailureConfEvent(short _error) {
		this.error = _error;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAUniversalFailure.encode(this.error, memberStream);
	}

	public static CSTAUniversalFailureConfEvent decode(InputStream in) {
		CSTAUniversalFailureConfEvent _this = new CSTAUniversalFailureConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.error = CSTAUniversalFailure.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAUniversalFailureConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAUniversalFailure.print(this.error, "error", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 53;
	}

	public short getError() {
		return this.error;
	}

	public void setError(short error) {
		this.error = error;
	}
}