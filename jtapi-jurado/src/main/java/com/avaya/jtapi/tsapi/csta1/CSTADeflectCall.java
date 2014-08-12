package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTADeflectCall extends CSTARequest {
	CSTAConnectionID deflectCall;
	String calledDevice;
	public static final int PDU = 15;

	public CSTADeflectCall() {
	}

	public CSTADeflectCall(CSTAConnectionID _deflectCall, String _calledDevice) {
		this.deflectCall = _deflectCall;
		this.calledDevice = _calledDevice;
	}

	public static CSTADeflectCall decode(InputStream in) {
		CSTADeflectCall _this = new CSTADeflectCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.deflectCall = CSTAConnectionID.decode(memberStream);
		this.calledDevice = DeviceID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.deflectCall, memberStream);
		DeviceID.encode(this.calledDevice, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADeflectCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.deflectCall, "deflectCall",
				indent));
		lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 15;
	}

	public String getCalledDevice() {
		return this.calledDevice;
	}

	public CSTAConnectionID getDeflectCall() {
		return this.deflectCall;
	}

	public void setCalledDevice(String _calledDevice) {
		this.calledDevice = _calledDevice;
	}

	public void setDeflectCall(CSTAConnectionID _deflectCall) {
		this.deflectCall = _deflectCall;
	}
}