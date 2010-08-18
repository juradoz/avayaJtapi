package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSSetPrivilegesConfEvent extends ACSConfirmation {
	public static final int PDU = 20;

	public static ACSSetPrivilegesConfEvent decode(final InputStream in) {
		final ACSSetPrivilegesConfEvent _this = new ACSSetPrivilegesConfEvent();

		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
	}

	@Override
	public int getPDU() {
		return 20;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSSetPrivilegesConfEvent ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}
}
