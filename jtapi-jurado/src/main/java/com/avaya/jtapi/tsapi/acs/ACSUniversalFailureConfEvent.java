package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSUniversalFailureConfEvent extends ACSConfirmation {
	short error;
	public static final int PDU = 6;

	public ACSUniversalFailureConfEvent() {
	}

	public ACSUniversalFailureConfEvent(short _error) {
		this.error = _error;
	}

	public void encodeMembers(OutputStream memberStream) {
		ACSUniversalFailure.encode(this.error, memberStream);
	}

	public static ACSUniversalFailureConfEvent decode(InputStream in) {
		ACSUniversalFailureConfEvent _this = new ACSUniversalFailureConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.error = ACSUniversalFailure.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSUniversalFailureConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ACSUniversalFailure.print(this.error, "error", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 6;
	}

	public short getError() {
		return this.error;
	}
}