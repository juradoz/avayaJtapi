package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTATransferCall extends CSTARequest {
	public static CSTATransferCall decode(final InputStream in) {
		final CSTATransferCall _this = new CSTATransferCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID heldCall;
	CSTAConnectionID activeCall;

	public static final int PDU = 51;

	public CSTATransferCall() {
	}

	public CSTATransferCall(final CSTAConnectionID _heldCall,
			final CSTAConnectionID _activeCall) {
		heldCall = _heldCall;
		activeCall = _activeCall;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		heldCall = CSTAConnectionID.decode(memberStream);
		activeCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(heldCall, memberStream);
		CSTAConnectionID.encode(activeCall, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	public CSTAConnectionID getHeldCall() {
		return heldCall;
	}

	@Override
	public int getPDU() {
		return 51;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTATransferCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(heldCall, "heldCall", indent));
		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));

		lines.add("}");
		return lines;
	}
}
