package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSClientHeartbeatEvent extends ACSUnsolicited {
	public static final int PDU = 16;

	public static ACSClientHeartbeatEvent decode(InputStream in) {
		ACSClientHeartbeatEvent _this = new ACSClientHeartbeatEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
	}

	@Override
	public int getPDU() {
		return 16;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("ACSClientHeartbeatEvent ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSClientHeartbeatEvent JD-Core Version: 0.5.4
 */