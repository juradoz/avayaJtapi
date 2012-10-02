package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMonitorCall extends CSTARequest {
	CSTAConnectionID call;
	CSTAMonitorFilter monitorFilter;
	public static final int PDU = 112;

	public CSTAMonitorCall(CSTAConnectionID _call,
			CSTAMonitorFilter _monitorFilter) {
		this.call = _call;
		this.monitorFilter = _monitorFilter;
	}

	public CSTAMonitorCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.call, memberStream);
		CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
	}

	public static CSTAMonitorCall decode(InputStream in) {
		CSTAMonitorCall _this = new CSTAMonitorCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.call = CSTAConnectionID.decode(memberStream);
		this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMonitorCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
		lines.addAll(CSTAMonitorFilter.print(this.monitorFilter,
				"monitorFilter", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 112;
	}

	public CSTAConnectionID getCall() {
		return this.call;
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return this.monitorFilter;
	}
}