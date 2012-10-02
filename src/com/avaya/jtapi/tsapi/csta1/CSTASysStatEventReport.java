package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class CSTASysStatEventReport extends CSTAEventReport {
	public static final int PDU = 106;
	private short state;

	public int getPDU() {
		return 106;
	}

	public static CSTASysStatEventReport decode(InputStream in) {
		CSTASysStatEventReport _this = new CSTASysStatEventReport();
		_this.doDecode(in);
		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		SystemStatus.encode(this.state, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.state = SystemStatus.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASysStatEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.addAll(SystemStatus.print(this.state, "state", indent));
		lines.add("}");
		return lines;
	}

	public short getState() {
		return this.state;
	}

	public void setState(short state) {
		this.state = state;
	}
}