package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV9ServiceInitiatedEvent extends LucentServiceInitiatedEvent {
	static final int PDU = 147;
	private short consultMode;

	public static LucentV9ServiceInitiatedEvent decode(InputStream in) {
		LucentV9ServiceInitiatedEvent lucentV9ServiceInitiatedEvent = new LucentV9ServiceInitiatedEvent();
		lucentV9ServiceInitiatedEvent.doDecode(in);
		return lucentV9ServiceInitiatedEvent;
	}

	public short getConsultMode() {
		return this.consultMode;
	}

	public void setConsultMode(short consultMode) {
		this.consultMode = consultMode;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.consultMode = LucentConsultMode.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentConsultMode.encode(this.consultMode, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV9ServiceInitiatedEvent ::=");
		lines.add("{");
		String indent = "  ";
		lines.addAll(UCID.print(this.ucid, "ucid", indent));
		lines.addAll(LucentConsultMode.print(this.consultMode, "consultMode",
				indent));
		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 147;
	}
}