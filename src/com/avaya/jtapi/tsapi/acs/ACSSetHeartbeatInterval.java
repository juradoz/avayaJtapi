package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class ACSSetHeartbeatInterval extends ACSRequest {
	public static ACSSetHeartbeatInterval decode(InputStream in) {
		ACSSetHeartbeatInterval _this = new ACSSetHeartbeatInterval();
		_this.doDecode(in);

		return _this;
	}

	short heartbeatInterval;

	public static final int PDU = 14;

	public ACSSetHeartbeatInterval() {
	}

	public ACSSetHeartbeatInterval(short heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		heartbeatInterval = (short) ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(heartbeatInterval, memberStream);
	}

	public synchronized short getHeartbeatInterval() {
		return heartbeatInterval;
	}

	@Override
	public int getPDU() {
		return 14;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSSetHeartbeatInterval ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(heartbeatInterval, "heartbeatInterval",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatInterval JD-Core Version: 0.5.4
 */