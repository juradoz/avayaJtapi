package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class CSTAHoldCall extends CSTARequest {
	public static CSTAHoldCall decode(final InputStream in) {
		final CSTAHoldCall _this = new CSTAHoldCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID activeCall;
	boolean reservation;

	public static final int PDU = 21;

	public CSTAHoldCall() {
	}

	public CSTAHoldCall(final CSTAConnectionID _activeCall,
			final boolean _reservation) {
		activeCall = _activeCall;
		reservation = _reservation;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		reservation = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(activeCall, memberStream);
		ASNBoolean.encode(reservation, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	@Override
	public int getPDU() {
		return 21;
	}

	public boolean isReservation() {
		return reservation;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAHoldCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(ASNBoolean.print(reservation, "reservation", indent));

		lines.add("}");
		return lines;
	}
}
