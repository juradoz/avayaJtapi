package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTADeflectCall extends CSTARequest {
	CSTAConnectionID deflectCall;
	String calledDevice;
	public static final int PDU = 15;

	public static CSTADeflectCall decode(final InputStream in) {
		final CSTADeflectCall _this = new CSTADeflectCall();
		_this.doDecode(in);

		return _this;
	}

	public CSTADeflectCall() {
	}

	public CSTADeflectCall(final CSTAConnectionID _deflectCall,
			final String _calledDevice) {
		deflectCall = _deflectCall;
		calledDevice = _calledDevice;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		deflectCall = CSTAConnectionID.decode(memberStream);
		calledDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		return 15;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADeflectCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(deflectCall, "deflectCall", indent));
		lines.addAll(ASNIA5String.print(calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}

	public void setCalledDevice(final String _calledDevice) {
		calledDevice = _calledDevice;
	}

	public void setDeflectCall(final CSTAConnectionID _deflectCall) {
		deflectCall = _deflectCall;
	}
}
