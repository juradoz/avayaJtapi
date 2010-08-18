package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTAMonitorEndedEvent extends CSTAUnsolicited {
	short cause;
	public static final int PDU = 119;

	public static CSTAMonitorEndedEvent decode(InputStream in) {
		CSTAMonitorEndedEvent _this = new CSTAMonitorEndedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	@Override
	public int getPDU() {
		return 119;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorEndedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}
}

