package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class ACSSetHeartbeatIntervalConfEvent extends ACSConfirmation {
	public static ACSSetHeartbeatIntervalConfEvent decode(InputStream in) {
		ACSSetHeartbeatIntervalConfEvent _this = new ACSSetHeartbeatIntervalConfEvent();

		_this.doDecode(in);

		return _this;
	}

	short heartbeatInterval;

	public static final int PDU = 15;

	public ACSSetHeartbeatIntervalConfEvent() {
	}

	public ACSSetHeartbeatIntervalConfEvent(short heartbeatInterval) {
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
		return 15;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSSetHeartbeatIntervalConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(heartbeatInterval, "heartbeatInterval",
				indent));

		lines.add("}");
		return lines;
	}

	public void setHeartbeatInterval(short heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatIntervalConfEvent JD-Core Version:
 * 0.5.4
 */