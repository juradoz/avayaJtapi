package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAReconnectCall extends CSTARequest {
	CSTAConnectionID activeCall;
	CSTAConnectionID heldCall;
	public static final int PDU = 39;

	public static CSTAReconnectCall decode(InputStream in) {
		CSTAReconnectCall _this = new CSTAReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	protected CSTAReconnectCall() {
	}

	public CSTAReconnectCall(CSTAConnectionID _activeCall,
			CSTAConnectionID _heldCall) {
		activeCall = _activeCall;
		heldCall = _heldCall;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		heldCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTAReconnectCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(CSTAConnectionID.print(heldCall, "heldCall", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAReconnectCall JD-Core Version: 0.5.4
 */