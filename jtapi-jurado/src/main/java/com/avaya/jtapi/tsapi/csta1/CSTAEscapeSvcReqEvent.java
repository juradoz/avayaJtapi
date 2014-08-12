package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAEscapeSvcReqEvent extends CSTAUnsolicited {
	static final int PDU = 91;

	public static CSTAEscapeSvcReqEvent decode(InputStream in) {
		CSTAEscapeSvcReqEvent _this = new CSTAEscapeSvcReqEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAEscapeSvcReqEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 91;
	}
}