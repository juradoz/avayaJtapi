package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAChangeMonitorFilterConfEvent extends CSTAConfirmation {
	CSTAMonitorFilter monitorFilter;
	public static final int PDU = 116;

	public static CSTAChangeMonitorFilterConfEvent decode(final InputStream in) {
		final CSTAChangeMonitorFilterConfEvent _this = new CSTAChangeMonitorFilterConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNSequence.encode(monitorFilter, memberStream);
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return monitorFilter;
	}

	@Override
	public int getPDU() {
		return 116;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAChangeMonitorFilterConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAMonitorFilter.print(monitorFilter, "monitorFilter",
				indent));

		lines.add("}");
		return lines;
	}
}
