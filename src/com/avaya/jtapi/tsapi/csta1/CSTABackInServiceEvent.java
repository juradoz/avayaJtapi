package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTABackInServiceEvent extends CSTAUnsolicited {
	String device;
	short cause;
	static final int PDU = 96;

	public static CSTABackInServiceEvent decode(InputStream in) {
		CSTABackInServiceEvent _this = new CSTABackInServiceEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.device = DeviceID.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTABackInServiceEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(DeviceID.print(this.device, "device", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 96;
	}

	public short getCause() {
		return this.cause;
	}

	public String getDevice() {
		return this.device;
	}
}