package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAConsultationCall extends CSTARequest {
	public static CSTAConsultationCall decode(final InputStream in) {
		final CSTAConsultationCall _this = new CSTAConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID activeCall;
	String calledDevice;

	public static final int PDU = 13;

	public CSTAConsultationCall() {
	}

	public CSTAConsultationCall(final CSTAConnectionID _activeCall,
			final String _calledDevice) {
		activeCall = _activeCall;
		calledDevice = _calledDevice;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		calledDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(activeCall, memberStream);
		ASNIA5String.encode(calledDevice, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	public String getCalledDevice() {
		return calledDevice;
	}

	@Override
	public int getPDU() {
		return 13;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConsultationCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(ASNIA5String.print(calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}
}
