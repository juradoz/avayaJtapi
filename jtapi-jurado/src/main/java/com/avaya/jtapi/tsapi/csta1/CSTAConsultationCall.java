package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAConsultationCall extends CSTARequest {
	CSTAConnectionID activeCall;
	String calledDevice;
	public static final int PDU = 13;

	public CSTAConsultationCall(CSTAConnectionID _activeCall,
			String _calledDevice) {
		this.activeCall = _activeCall;
		this.calledDevice = _calledDevice;
	}

	public CSTAConsultationCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.activeCall, memberStream);
		DeviceID.encode(this.calledDevice, memberStream);
	}

	public static CSTAConsultationCall decode(InputStream in) {
		CSTAConsultationCall _this = new CSTAConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.activeCall = CSTAConnectionID.decode(memberStream);
		this.calledDevice = DeviceID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConsultationCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall",
				indent));
		lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 13;
	}

	public CSTAConnectionID getActiveCall() {
		return this.activeCall;
	}

	public String getCalledDevice() {
		return this.calledDevice;
	}
}