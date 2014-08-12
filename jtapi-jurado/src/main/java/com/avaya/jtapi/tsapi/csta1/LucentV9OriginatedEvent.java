package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV9OriginatedEvent extends LucentV6OriginatedEvent {
	static final int PDU = 145;
	private short consultMode;

	public static LucentV9OriginatedEvent decode(InputStream in) {
		LucentV9OriginatedEvent lucentV9OriginatedEvent = new LucentV9OriginatedEvent();
		lucentV9OriginatedEvent.doDecode(in);
		return lucentV9OriginatedEvent;
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

		lines.add("LucentV9OriginatedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.physicalTerminal_asn,
				"physicalTerminal", indent));

		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));
		lines.addAll(LucentConsultMode.print(this.consultMode, "consultMode",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 145;
	}
}