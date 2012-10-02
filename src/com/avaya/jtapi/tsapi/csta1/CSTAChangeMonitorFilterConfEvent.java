package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAChangeMonitorFilterConfEvent extends CSTAConfirmation {
	CSTAMonitorFilter monitorFilter;
	public static final int PDU = 116;

	public static CSTAChangeMonitorFilterConfEvent decode(InputStream in) {
		CSTAChangeMonitorFilterConfEvent _this = new CSTAChangeMonitorFilterConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAChangeMonitorFilterConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAMonitorFilter.print(this.monitorFilter,
				"monitorFilter", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 116;
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return this.monitorFilter;
	}
}