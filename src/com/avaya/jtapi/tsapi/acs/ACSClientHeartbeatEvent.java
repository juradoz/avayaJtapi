package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSClientHeartbeatEvent extends ACSUnsolicited {
	public static final int PDU = 16;

	public static ACSClientHeartbeatEvent decode(final InputStream in) {
		final ACSClientHeartbeatEvent _this = new ACSClientHeartbeatEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
	}

	@Override
	public int getPDU() {
		return 16;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSClientHeartbeatEvent ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}
}
