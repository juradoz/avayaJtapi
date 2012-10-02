package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAPickupCall extends CSTARequest {
	CSTAConnectionID deflectCall;
	String calledDevice;
	public static final int PDU = 17;

	public CSTAPickupCall() {
	}

	public CSTAPickupCall(CSTAConnectionID _deflectCall, String _calledDevice) {
		this.deflectCall = _deflectCall;
		this.calledDevice = _calledDevice;
	}

	public static CSTAPickupCall decode(InputStream in) {
		CSTAPickupCall _this = new CSTAPickupCall();
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

		lines.add("CSTAPickupCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.deflectCall, "deflectCall",
				indent));
		lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 17;
	}

	public String getCalledDevice() {
		return this.calledDevice;
	}

	public CSTAConnectionID getDeflectCall() {
		return this.deflectCall;
	}
}