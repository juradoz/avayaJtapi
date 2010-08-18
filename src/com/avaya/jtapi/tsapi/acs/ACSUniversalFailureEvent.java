package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSUniversalFailureEvent extends ACSUnsolicited {
	short error;
	public static final int PDU = 7;

	public static ACSUniversalFailureEvent decode(InputStream in) {
		ACSUniversalFailureEvent _this = new ACSUniversalFailureEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		error = ASNEnumerated.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 7;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSUniversalFailureEvent ::=");
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
 * com.avaya.jtapi.tsapi.acs.ACSUniversalFailureEvent JD-Core Version: 0.5.4
 */