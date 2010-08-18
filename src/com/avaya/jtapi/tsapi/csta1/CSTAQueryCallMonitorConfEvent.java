package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class CSTAQueryCallMonitorConfEvent extends CSTAConfirmation {
	boolean callMonitor;
	public static final int PDU = 129;

	public static CSTAQueryCallMonitorConfEvent decode(InputStream in) {
		CSTAQueryCallMonitorConfEvent _this = new CSTAQueryCallMonitorConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		callMonitor = ASNBoolean.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 129;
	}

	public boolean isCallMonitor() {
		return callMonitor;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAQueryCallMonitorConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(callMonitor, "callMonitor", indent));

		lines.add("}");
		return lines;
	}

	public void setCallMonitor(boolean callMonitor) {
		this.callMonitor = callMonitor;
	}
}
