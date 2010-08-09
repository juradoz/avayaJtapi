package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class CSTAHoldCall extends CSTARequest {
	public static CSTAHoldCall decode(InputStream in) {
		CSTAHoldCall _this = new CSTAHoldCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID activeCall;
	boolean reservation;

	public static final int PDU = 21;

	public CSTAHoldCall() {
	}

	public CSTAHoldCall(CSTAConnectionID _activeCall, boolean _reservation) {
		activeCall = _activeCall;
		reservation = _reservation;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		reservation = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("CSTAHoldCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(ASNBoolean.print(reservation, "reservation", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAHoldCall JD-Core Version: 0.5.4
 */