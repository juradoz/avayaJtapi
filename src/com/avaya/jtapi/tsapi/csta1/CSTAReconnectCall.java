package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAReconnectCall extends CSTARequest {
	CSTAConnectionID activeCall;
	CSTAConnectionID heldCall;
	public static final int PDU = 39;

	public static CSTAReconnectCall decode(final InputStream in) {
		final CSTAReconnectCall _this = new CSTAReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	protected CSTAReconnectCall() {
	}

	public CSTAReconnectCall(final CSTAConnectionID _activeCall,
			final CSTAConnectionID _heldCall) {
		activeCall = _activeCall;
		heldCall = _heldCall;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		heldCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(activeCall, memberStream);
		CSTAConnectionID.encode(heldCall, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	public CSTAConnectionID getHeldCall() {
		return heldCall;
	}

	@Override
	public int getPDU() {
		return 39;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAReconnectCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(CSTAConnectionID.print(heldCall, "heldCall", indent));

		lines.add("}");
		return lines;
	}
}
