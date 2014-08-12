package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAHoldCall extends CSTARequest {
	CSTAConnectionID activeCall;
	boolean reservation;
	public static final int PDU = 21;

	public CSTAHoldCall(CSTAConnectionID _activeCall, boolean _reservation) {
		this.activeCall = _activeCall;
		this.reservation = _reservation;
	}

	public CSTAHoldCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.activeCall, memberStream);
		ASNBoolean.encode(this.reservation, memberStream);
	}

	public static CSTAHoldCall decode(InputStream in) {
		CSTAHoldCall _this = new CSTAHoldCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.activeCall = CSTAConnectionID.decode(memberStream);
		this.reservation = ASNBoolean.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAHoldCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall",
				indent));
		lines.addAll(ASNBoolean.print(this.reservation, "reservation", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 21;
	}

	public CSTAConnectionID getActiveCall() {
		return this.activeCall;
	}

	public boolean isReservation() {
		return this.reservation;
	}
}