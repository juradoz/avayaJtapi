package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAReconnectCall extends CSTARequest {
	CSTAConnectionID activeCall;
	CSTAConnectionID heldCall;
	public static final int PDU = 39;

	public CSTAReconnectCall(CSTAConnectionID _activeCall,
			CSTAConnectionID _heldCall) {
		this.activeCall = _activeCall;
		this.heldCall = _heldCall;
	}

	protected CSTAReconnectCall() {
	}

	public static CSTAReconnectCall decode(InputStream in) {
		CSTAReconnectCall _this = new CSTAReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.activeCall = CSTAConnectionID.decode(memberStream);
		this.heldCall = CSTAConnectionID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.activeCall, memberStream);
		CSTAConnectionID.encode(this.heldCall, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAReconnectCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall",
				indent));
		lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 39;
	}

	public CSTAConnectionID getActiveCall() {
		return this.activeCall;
	}

	public CSTAConnectionID getHeldCall() {
		return this.heldCall;
	}
}