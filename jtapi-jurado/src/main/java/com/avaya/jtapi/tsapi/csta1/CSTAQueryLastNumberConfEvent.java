package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAQueryLastNumberConfEvent extends CSTAConfirmation {
	String lastNumber;
	static final int PDU = 36;

	public static CSTAQueryLastNumberConfEvent decode(InputStream in) {
		CSTAQueryLastNumberConfEvent _this = new CSTAQueryLastNumberConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.lastNumber = DeviceID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryLastNumberConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.lastNumber, "lastNumber", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 36;
	}

	public String getLastNumber() {
		return this.lastNumber;
	}
}