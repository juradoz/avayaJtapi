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

	public static CSTAMonitorConfEvent decode(InputStream in) {
		CSTAMonitorConfEvent _this = new CSTAMonitorConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		monitorCrossRefID = ASNInteger.decode(memberStream);
		monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(monitorCrossRefID, "monitorCrossRefID",
				indent));
		lines.addAll(CSTAMonitorFilter.print(monitorFilter, "monitorFilter",
				indent));

		lines.add("}");
		return lines;
	}

	public void setMonitorCrossRefID(int crossRef) {
		monitorCrossRefID = crossRef;
	}

	public void setMonitorFilter(CSTAMonitorFilter filter) {
		monitorFilter = filter;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent JD-Core Version: 0.5.4
 */