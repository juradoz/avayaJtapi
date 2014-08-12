package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASetMwiConfEvent extends CSTAConfirmation {
	public static final int PDU = 44;

	public static CSTASetMwiConfEvent decode(InputStream in) {
		CSTASetMwiConfEvent _this = new CSTASetMwiConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetMwiConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 44;
	}
}