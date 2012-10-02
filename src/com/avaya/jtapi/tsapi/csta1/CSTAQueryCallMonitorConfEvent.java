package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAQueryCallMonitorConfEvent extends CSTAConfirmation {
	boolean callMonitor;
	public static final int PDU = 129;

	public static CSTAQueryCallMonitorConfEvent decode(InputStream in) {
		CSTAQueryCallMonitorConfEvent _this = new CSTAQueryCallMonitorConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.callMonitor = ASNBoolean.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAQueryCallMonitorConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(this.callMonitor, "callMonitor", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 129;
	}

	public boolean isCallMonitor() {
		return this.callMonitor;
	}

	public void setCallMonitor(boolean callMonitor) {
		this.callMonitor = callMonitor;
	}
}