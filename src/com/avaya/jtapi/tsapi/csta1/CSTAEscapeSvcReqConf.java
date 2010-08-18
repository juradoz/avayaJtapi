package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTAEscapeSvcReqConf extends CSTARequest {
	short errorValue;
	static final int PDU = 92;

	public CSTAEscapeSvcReqConf(short _errorValue) {
		errorValue = _errorValue;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAEscapeSvcReqConf ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAUniversalFailure.print(errorValue, "errorValue",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcReqConf JD-Core Version: 0.5.4
 */