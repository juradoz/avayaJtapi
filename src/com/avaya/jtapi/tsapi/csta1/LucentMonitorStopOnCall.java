package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentMonitorStopOnCall extends LucentPrivateData {
	int monitorCrossRefID;
	CSTAConnectionID call;
	static final int PDU = 31;

	public LucentMonitorStopOnCall(final int _monitorCrossRefID,
			final CSTAConnectionID _call) {
		monitorCrossRefID = _monitorCrossRefID;
		call = _call;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(monitorCrossRefID, memberStream);
		CSTAConnectionID.encode(call, memberStream);
	}

	@Override
	public int getPDU() {
		return 31;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorStopOnCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(monitorCrossRefID, "monitorCrossRefID",
				indent));
		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}
