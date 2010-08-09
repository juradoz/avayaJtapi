package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAConferenceCall extends CSTARequest {
	public static CSTAConferenceCall decode(InputStream in) {
		CSTAConferenceCall _this = new CSTAConferenceCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID heldCall;
	CSTAConnectionID activeCall;

	public static final int PDU = 11;

	public CSTAConferenceCall() {
	}

	public CSTAConferenceCall(CSTAConnectionID _heldCall,
			CSTAConnectionID _activeCall) {
		heldCall = _heldCall;
		activeCall = _activeCall;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		heldCall = CSTAConnectionID.decode(memberStream);
		activeCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		return 11;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("CSTAConferenceCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(heldCall, "heldCall", indent));
		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAConferenceCall JD-Core Version: 0.5.4
 */