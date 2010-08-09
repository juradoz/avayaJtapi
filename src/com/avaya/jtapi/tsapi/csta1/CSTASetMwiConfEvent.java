package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNNull;

public final class CSTASetMwiConfEvent extends CSTAConfirmation {
	public static final int PDU = 44;

	public static CSTASetMwiConfEvent decode(InputStream in) {
		CSTASetMwiConfEvent _this = new CSTASetMwiConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 44;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("CSTASetMwiConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASetMwiConfEvent JD-Core Version: 0.5.4
 */