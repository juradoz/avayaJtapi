package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTAEscapeSvcReqConf extends CSTARequest {
	short errorValue;
	static final int PDU = 92;

	public CSTAEscapeSvcReqConf(final short _errorValue) {
		errorValue = _errorValue;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(errorValue, memberStream);
	}

	public short getErrorValue() {
		return errorValue;
	}

	@Override
	public int getPDU() {
		return 92;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAEscapeSvcReqConf ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAUniversalFailure.print(errorValue, "errorValue",
				indent));

		lines.add("}");
		return lines;
	}
}
