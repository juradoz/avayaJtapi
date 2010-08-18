package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentMonitorStopOnCall extends LucentPrivateData {
	int monitorCrossRefID;
	CSTAConnectionID call;
	static final int PDU = 31;

	public LucentMonitorStopOnCall(int _monitorCrossRefID,
			CSTAConnectionID _call) {
		monitorCrossRefID = _monitorCrossRefID;
		call = _call;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(monitorCrossRefID, memberStream);
		CSTAConnectionID.encode(call, memberStream);
	}

	@Override
	public int getPDU() {
		return 31;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorStopOnCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(monitorCrossRefID, "monitorCrossRefID",
				indent));
		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentMonitorStopOnCall JD-Core Version: 0.5.4
 */