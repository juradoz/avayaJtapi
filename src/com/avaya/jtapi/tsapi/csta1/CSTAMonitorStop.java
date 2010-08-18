package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTAMonitorStop extends CSTARequest {
	public static CSTAMonitorStop decode(final InputStream in) {
		final CSTAMonitorStop _this = new CSTAMonitorStop();
		_this.doDecode(in);

		return _this;
	}

	int monitorCrossRefID;

	public static final int PDU = 117;

	public CSTAMonitorStop() {
	}

	public CSTAMonitorStop(final int _monitorCrossRefID) {
		monitorCrossRefID = _monitorCrossRefID;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		monitorCrossRefID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(monitorCrossRefID, memberStream);
	}

	public int getMonitorCrossRefID() {
		return monitorCrossRefID;
	}

	@Override
	public int getPDU() {
		return 117;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorStop ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(monitorCrossRefID, "monitorCrossRefID",
				indent));

		lines.add("}");
		return lines;
	}
}
