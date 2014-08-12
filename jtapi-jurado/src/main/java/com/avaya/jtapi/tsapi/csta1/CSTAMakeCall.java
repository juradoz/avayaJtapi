package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMakeCall extends CSTARequest {
	String callingDevice;
	String calledDevice;
	public static final int PDU = 23;

	public CSTAMakeCall(String _callingDevice, String _calledDevice) {
		this.callingDevice = _callingDevice;
		this.calledDevice = _calledDevice;
	}

	public CSTAMakeCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.callingDevice, memberStream);
		DeviceID.encode(this.calledDevice, memberStream);
	}

	public static CSTAMakeCall decode(InputStream in) {
		CSTAMakeCall _this = new CSTAMakeCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.callingDevice = DeviceID.decode(memberStream);
		this.calledDevice = DeviceID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMakeCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID
				.print(this.callingDevice, "callingDevice", indent));
		lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 23;
	}

	public String getCalledDevice() {
		return this.calledDevice;
	}

	public String getCallingDevice() {
		return this.callingDevice;
	}
}