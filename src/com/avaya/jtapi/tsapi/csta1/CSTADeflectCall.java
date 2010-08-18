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

	public static CSTADeflectCall decode(InputStream in) {
		CSTADeflectCall _this = new CSTADeflectCall();
		_this.doDecode(in);

		return _this;
	}

	public CSTADeflectCall() {
	}

	public CSTADeflectCall(CSTAConnectionID _deflectCall, String _calledDevice) {
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
		return 15;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADeflectCall ::=");
		lines.add("{");

		String indent = "  ";

		lines
				.addAll(CSTAConnectionID.print(deflectCall, "deflectCall",
						indent));
		lines.addAll(ASNIA5String.print(calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}

	public void setCalledDevice(String _calledDevice) {
		calledDevice = _calledDevice;
	}

	public void setDeflectCall(CSTAConnectionID _deflectCall) {
		deflectCall = _deflectCall;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTADeflectCall JD-Core Version: 0.5.4
 */