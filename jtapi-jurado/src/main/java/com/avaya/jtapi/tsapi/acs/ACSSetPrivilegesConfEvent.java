package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSSetPrivilegesConfEvent extends ACSConfirmation {
	public static final int PDU = 20;

	public static ACSSetPrivilegesConfEvent decode(InputStream in) {
		ACSSetPrivilegesConfEvent _this = new ACSSetPrivilegesConfEvent();

		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
	}

	public void decodeMembers(InputStream memberStream) {
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSSetPrivilegesConfEvent ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 20;
	}
}