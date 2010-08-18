package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAMakeCall extends CSTARequest {
	public static CSTAMakeCall decode(InputStream in) {
		CSTAMakeCall _this = new CSTAMakeCall();
		_this.doDecode(in);

		return _this;
	}

	String callingDevice;
	String calledDevice;

	public static final int PDU = 23;

	public CSTAMakeCall() {
	}

	public CSTAMakeCall(String _callingDevice, String _calledDevice) {
		callingDevice = _callingDevice;
		calledDevice = _calledDevice;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		callingDevice = ASNIA5String.decode(memberStream);
		calledDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(callingDevice, memberStream);
		ASNIA5String.encode(calledDevice, memberStream);
	}

	public String getCalledDevice() {
		return calledDevice;
	}

	public String getCallingDevice() {
		return callingDevice;
	}

	@Override
	public int getPDU() {
		return 23;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMakeCall ::=");
		lines.add("{");

		String indent = "  ";

		lines
				.addAll(ASNIA5String.print(callingDevice, "callingDevice",
						indent));
		lines.addAll(ASNIA5String.print(calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}
}

