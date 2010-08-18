package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAMonitorConfEvent extends CSTAConfirmation {
	int monitorCrossRefID;
	CSTAMonitorFilter monitorFilter;
	public static final int PDU = 114;

	public static CSTAMonitorConfEvent decode(final InputStream in) {
		final CSTAMonitorConfEvent _this = new CSTAMonitorConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		monitorCrossRefID = ASNInteger.decode(memberStream);
		monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(monitorCrossRefID, memberStream);
		ASNSequence.encode(monitorFilter, memberStream);
	}

	public int getMonitorCrossRefID() {
		return monitorCrossRefID;
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return monitorFilter;
	}

	@Override
	public int getPDU() {
		return 114;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(monitorCrossRefID, "monitorCrossRefID",
				indent));
		lines.addAll(CSTAMonitorFilter.print(monitorFilter, "monitorFilter",
				indent));

		lines.add("}");
		return lines;
	}

	public void setMonitorCrossRefID(final int crossRef) {
		monitorCrossRefID = crossRef;
	}

	public void setMonitorFilter(final CSTAMonitorFilter filter) {
		monitorFilter = filter;
	}
}
