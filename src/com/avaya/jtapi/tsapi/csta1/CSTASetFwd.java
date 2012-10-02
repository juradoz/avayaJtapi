package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASetFwd extends CSTARequest {
	String device;
	CSTAForwardingInfo forward;
	public static final int PDU = 47;

	public CSTASetFwd() {
	}

	public CSTASetFwd(String _device, CSTAForwardingInfo _forward) {
		this.device = _device;
		this.forward = _forward;
	}

	public static CSTASetFwd decode(InputStream in) {
		CSTASetFwd _this = new CSTASetFwd();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.device = DeviceID.decode(memberStream);
		this.forward = CSTAForwardingInfo.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.device, memberStream);
		CSTAForwardingInfo.encode(this.forward, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetFwd ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));
		lines.addAll(CSTAForwardingInfo.print(this.forward, "forward", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 47;
	}

	public String getDevice() {
		return this.device;
	}

	public CSTAForwardingInfo getForward() {
		return this.forward;
	}
}