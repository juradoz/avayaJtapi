package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAQueryLastNumberConfEvent extends CSTAConfirmation {
	String lastNumber;
	static final int PDU = 36;

	public static CSTAQueryLastNumberConfEvent decode(InputStream in) {
		CSTAQueryLastNumberConfEvent _this = new CSTAQueryLastNumberConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		lastNumber = ASNIA5String.decode(memberStream);
	}

	public String getLastNumber() {
		return lastNumber;
	}

	@Override
	public int getPDU() {
		return 36;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryLastNumberConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(lastNumber, "lastNumber", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAQueryLastNumberConfEvent JD-Core Version:
 * 0.5.4
 */