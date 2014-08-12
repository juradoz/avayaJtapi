package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV9HeldEvent extends LucentPrivateData {
	static final int PDU = 146;
	private short consultMode;

	public static LucentV9HeldEvent decode(InputStream in) {
		LucentV9HeldEvent lucentV9HeldEvent = new LucentV9HeldEvent();
		lucentV9HeldEvent.doDecode(in);
		return lucentV9HeldEvent;
	}

	public void decodeMembers(InputStream memberStream) {
		this.consultMode = LucentConsultMode.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		LucentConsultMode.encode(this.consultMode, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV9HeldEvent ::=");
		lines.add("{");
		String indent = "  ";
		lines.addAll(LucentConsultMode.print(this.consultMode, "consultMode",
				indent));
		lines.add("}");
		return lines;
	}

	public short getConsultMode() {
		return this.consultMode;
	}

	public void setConsultMode(short consultMode) {
		this.consultMode = consultMode;
	}

	public int getPDU() {
		return 146;
	}
}