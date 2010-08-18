package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAMonitorCall extends CSTARequest {
	public static CSTAMonitorCall decode(final InputStream in) {
		final CSTAMonitorCall _this = new CSTAMonitorCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID call;
	CSTAMonitorFilter monitorFilter;

	public static final int PDU = 112;

	public CSTAMonitorCall() {
	}

	public CSTAMonitorCall(final CSTAConnectionID _call,
			final CSTAMonitorFilter _monitorFilter) {
		call = _call;
		monitorFilter = _monitorFilter;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		call = CSTAConnectionID.decode(memberStream);
		monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(call, memberStream);
		ASNSequence.encode(monitorFilter, memberStream);
	}

	public CSTAConnectionID getCall() {
		return call;
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return monitorFilter;
	}

	@Override
	public int getPDU() {
		return 112;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMonitorCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(call, "call", indent));
		lines.addAll(CSTAMonitorFilter.print(monitorFilter, "monitorFilter",
				indent));

		lines.add("}");
		return lines;
	}
}
