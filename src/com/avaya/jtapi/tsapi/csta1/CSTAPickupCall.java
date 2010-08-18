package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAPickupCall extends CSTARequest {
	CSTAConnectionID deflectCall;
	String calledDevice;
	public static final int PDU = 17;

	public static CSTAPickupCall decode(InputStream in) {
		CSTAPickupCall _this = new CSTAPickupCall();
		_this.doDecode(in);

		return _this;
	}

	public CSTAPickupCall() {
	}

	public CSTAPickupCall(CSTAConnectionID _deflectCall, String _calledDevice) {
		deflectCall = _deflectCall;
		calledDevice = _calledDevice;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deflectCall = CSTAConnectionID.decode(memberStream);
		calledDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(deflectCall, memberStream);
		ASNIA5String.encode(calledDevice, memberStream);
	}

	public String getCalledDevice() {
		return calledDevice;
	}

	public CSTAConnectionID getDeflectCall() {
		return deflectCall;
	}

	@Override
	public int getPDU() {
		return 17;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAPickupCall ::=");
		lines.add("{");

		String indent = "  ";

		lines
				.addAll(CSTAConnectionID.print(deflectCall, "deflectCall",
						indent));
		lines.addAll(ASNIA5String.print(calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}
}

