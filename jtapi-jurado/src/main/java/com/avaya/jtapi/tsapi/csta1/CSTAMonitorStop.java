package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMonitorStop extends CSTARequest {
	int monitorCrossRefID;
	public static final int PDU = 117;

	public CSTAMonitorStop() {
	}

	public CSTAMonitorStop(int _monitorCrossRefID) {
		this.monitorCrossRefID = _monitorCrossRefID;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAMonitorCrossRefID.encode(this.monitorCrossRefID, memberStream);
	}

	public static CSTAMonitorStop decode(InputStream in) {
		CSTAMonitorStop _this = new CSTAMonitorStop();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.monitorCrossRefID = CSTAMonitorCrossRefID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorStop ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID,
				"monitorCrossRefID", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 117;
	}

	public int getMonitorCrossRefID() {
		return this.monitorCrossRefID;
	}
}