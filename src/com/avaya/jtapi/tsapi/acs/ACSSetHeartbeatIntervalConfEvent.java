package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class ACSSetHeartbeatIntervalConfEvent extends ACSConfirmation {
	public static ACSSetHeartbeatIntervalConfEvent decode(final InputStream in) {
		final ACSSetHeartbeatIntervalConfEvent _this = new ACSSetHeartbeatIntervalConfEvent();

		_this.doDecode(in);

		return _this;
	}

	short heartbeatInterval;

	public static final int PDU = 15;

	public ACSSetHeartbeatIntervalConfEvent() {
	}

	public ACSSetHeartbeatIntervalConfEvent(final short heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		heartbeatInterval = (short) ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSSetHeartbeatIntervalConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(heartbeatInterval, "heartbeatInterval",
				indent));

		lines.add("}");
		return lines;
	}

	public void setHeartbeatInterval(final short heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}
}
